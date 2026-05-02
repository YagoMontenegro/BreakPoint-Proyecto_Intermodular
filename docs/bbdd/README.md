# Base de datos – BreakPoint Club de Billar
### Módulo: Bases de Datos (0484) – 1º DAW

---

## 1. Descripción del proyecto

BreakPoint es un club de billar ficticio que necesita gestionar toda su actividad de forma ordenada. El club tiene usuarios que reservan mesas, algunos de esos usuarios son socios con cuota mensual, y el club organiza torneos en los que solo pueden participar los socios.

El objetivo de esta base de datos es modelar exactamente eso: quién es quién, qué hace cada uno, y cómo se relacionan todas las partes del negocio entre sí.

La base de datos está conectada a una aplicación Java mediante JDBC (desarrollada en el módulo de Programación) que permite gestionar todas las entidades de forma interactiva. Aquí nos centramos en que la estructura sea correcta y tenga sentido real.

---

## 2. Análisis del negocio

Antes de ponerse a crear tablas, lo primero que hice fue pensar en cómo funciona realmente un club de billar y qué información maneja.

**¿Qué hace un club de billar?**

- Tiene mesas que la gente puede reservar por horas
- Algunas personas se hacen socias y pagan una cuota mensual
- Los socios tienen ventajas: reservan gratis y pueden jugar en torneos
- El club organiza torneos periódicamente
- Necesita saber quién le debe dinero, qué mesas están ocupadas y qué torneos tiene activos

A partir de ahí saqué las entidades principales:

| Entidad | ¿Qué representa? |
|---|---|
| `usuarios` | Cualquier persona que interactúa con el club |
| `socios` | Usuarios que tienen membresía activa |
| `cuotas_socios` | El pago mensual de cada socio |
| `mesas` | Las mesas de billar del local |
| `reservas` | Cuándo una mesa está ocupada y por quién |
| `torneos` | Competiciones organizadas por el club |
| `inscripciones` | Qué socios participan en cada torneo |

---

## 3. Decisiones de diseño y por qué


---

### 3.1 Separar `usuarios` de `socios`

Esta fue la primera decisión importante, y creo que es la más relevante del diseño.

Se podría haber metido todo en una sola tabla `clientes` con un campo `es_socio`, pero eso da problemas. Un usuario puede reservar una mesa sin ser socio. Cuando se hace socio, pasa a tener información adicional (fecha de alta, cuotas, etc.). Si mezclo todo en una tabla, tengo muchos campos nulos para los no socios.

La solución fue separar ambos conceptos: `usuarios` es para todo el mundo, y `socios` es una extensión de `usuarios` para los que tienen membresía. La relación es `1:1` (un usuario solo puede ser socio una vez, y un registro de socio siempre pertenece a un único usuario).


Esto también refleja la realidad del negocio: puedes cancelar la membresía (estado_socio = 'cancelado') manteniendo el histórico al completo.

---

### 3.2 La tabla `cuotas_socios` y por qué no guardar solo el estado de pago en `socios`

Otra opción hubiera sido poner `cuota_pagada = true/false` directamente en la tabla `socios`. Pero eso no permite llevar un histórico. ¿Cuánto recaudó el club en marzo? ¿Este socio debe cuotas de hace tres meses? Con un simple booleano, eso es imposible de saber.

La tabla `cuotas_socios` genera un registro por cada mes y socio. Así se puede consultar el historial completo de pagos de cualquier socio y sacar estadísticas de recaudación.

Hay una restricción `UNIQUE (id_socio, mes, anio)` para que no pueda existir el mismo mes dos veces para el mismo socio. Eso evita duplicados.

---

### 3.3 La tabla `reservas` y su clave primaria compuesta

La tabla `reservas` usa una **clave primaria compuesta** formada por `(id_usuario, id_mesa, hora_inicio)`. 

El razonamiento es el siguiente: no tiene sentido que el mismo usuario reserve la misma mesa a la misma hora dos veces. Y tampoco tiene sentido que dos usuarios distintos tengan la misma mesa a la misma hora. Con esa combinación de tres campos como clave primaria, ambas situaciones quedan bloqueadas por la propia base de datos, sin necesidad de hacer comprobaciones desde el código.

El campo `coste` está en la reserva (no en el usuario ni en la mesa) porque depende de si el usuario es socio en el momento de hacer la reserva. Los socios pagan 0€/h y los no socios 6€/h. Este cálculo lo hará la aplicación Java, pero el valor se guarda en la reserva para tener el histórico correcto aunque el usuario cambie de estado más adelante.

---

### 3.4 Los torneos solo son para socios

La tabla `inscripciones` referencia a `socios`, no a `usuarios`. Esto es intencionado: en el club, participar en torneos es un privilegio de los socios. Si hubiera puesto la relación con `usuarios`, cualquiera podría inscribirse.

El campo `resultado` en `inscripciones` es `NULL` hasta que el torneo termina. Cuando finaliza, se rellena con la posición final del socio (1 = campeón, 2 = segundo, etc.). Mientras el torneo está en curso, el resultado no se conoce, así que `NULL` aquí tiene un significado real.

---

### 3.5 Los ENUMs en lugar de tablas auxiliares

En varias tablas se usan campos `ENUM` en vez de tablas relacionadas con IDs. Por ejemplo, `estado_mesa` puede ser `'disponible'`, `'reservada'` o `'mantenimiento'`, al igual que `estado_cuota` o `modalidad`.

En un sistema más grande, habría una tabla `estados` con una foreign key. Pero para este proyecto, los valores posibles son pocos, bien definidos y es poco probable que cambien. Usar `ENUM` es más sencillo, eficiente y evita joins innecesarios.

---

### 3.6 Restricciones (`CHECK`) para proteger los datos

Se han añadido varias restricciones `CHECK` para que la base de datos no permita datos incoherentes:

- `CHECK (hora_fin > hora_inicio)` en `reservas`: una reserva no puede terminar antes de empezar
- `CHECK (fecha_fin > fecha_inicio)` en `torneos`: igual, un torneo no puede terminar antes de comenzar
- `CHECK (mes BETWEEN 1 AND 12)` en `cuotas_socios`: los meses solo van del 1 al 12
- `CHECK (importe > 0)` en `cuotas_socios`: una cuota no puede tener importe negativo ni cero
- `CHECK (max_participantes > 0)` en `torneos`: un torneo necesita al menos un participante

Estas restricciones podrían hacerse también desde la aplicación, pero hacerlas en la base de datos las convierte en una red de seguridad adicional independiente del código.

---

## 4. Estructura de la base de datos

### Diagrama E/R

El diagrama entidad-relación se puede consultar en el archivo `docs/diagramas/Diagrama_ER.png`.

### Modelo relacional

El modelo relacional se puede consultar en `docs/diagramas/Modelo_relacional.png`.

### Tablas y relaciones resumidas

```
usuarios (id_usuario PK, nombre, apellidos, email UNIQUE, telefono UNIQUE, fecha_registro)
    │
    └──► socios (id_socio PK, id_usuario FK UNIQUE, fecha_alta, fecha_baja, estado_socio)
              │
              ├──► cuotas_socios (id_cuota PK, id_socio FK, mes, anio, estado_cuota, importe, fecha_pago)
              │         [UNIQUE: id_socio + mes + anio]
              │
              └──► inscripciones (id_socio FK + id_torneo FK → PK compuesta, fecha_inscripcion, resultado)
                        │
                        └──► torneos (id_torneo PK, nombre, modalidad, fecha_inicio, fecha_fin,
                                      max_participantes, premios, estado_torneo)

usuarios ──► reservas (id_usuario FK + id_mesa FK + hora_inicio → PK compuesta, hora_fin, coste, estado_reserva)
                 │
mesas ───────────┘
(id_mesa PK, estado_mesa)
```

---

## 5. Archivos del módulo

| Archivo | Contenido |
|---|---|
| `sql/create_tables.sql` | Creación de la base de datos y todas las tablas |
| `sql/insert_data.sql` | Datos de prueba simulados (70 usuarios, desde 2023 hasta 2026) |
| `sql/queries.sql` | Consultas útiles organizadas por categorías |
| `docs/diagramas/Diagrama_ER.png` | Diagrama entidad-relación |
| `docs/diagramas/Modelo_relacional.png` | Modelo relacional |

---

## 6. Consultas incluidas

Las consultas están en `sql/queries.sql` y están agrupadas por categoría:

**Gestión diaria de recepción**
- Ver qué mesas están disponibles en este momento
- Reservas del día actual con nombre del cliente y horario
- Buscar un usuario por email o teléfono
- Comprobar si un usuario es socio activo

**Gestión de socios y cuotas**
- Historial de cuotas de un socio concreto
- Recaudación total por mes y año
- Socios con 3 o más cuotas impagadas (para contactarles)

**Torneos**
- Torneos con inscripción abierta
- Clasificación final de un torneo
- Participantes apuntados a un torneo

**Reservas**
- Ingresos generados por reservas de no socios en un periodo
- Horas punta del club (para optimizar horarios)

**Análisis de negocio**
- Usuarios no socios con muchas reservas (candidatos para ofrecerles la membresía)

---

## 7. Relación con el portal web

La base de datos y el portal web están diseñados para representar el mismo negocio. El portal muestra la información pública del club (torneos disponibles, cómo hacerse socio, la tienda, el taller, etc..) y la base de datos gestiona lo que ocurre internamente: quién es socio, qué mesas hay reservadas, qué torneos están activos...

La aplicación Java conecta ambas partes: lee y escribe en esta base de datos mediante JDBC, permitiendo al personal del club gestionar usuarios, socios, cuotas, mesas, reservas, torneos e inscripciones desde una interfaz de consola. Un usuario que vea la página de torneos en la web y quiera apuntarse, es el mismo tipo de entidad que existe en la tabla `usuarios` de la base de datos.

---

*Módulo Bases de Datos (0484) – Proyecto Intermodular – 1º DAW – Curso 2024/2025*
