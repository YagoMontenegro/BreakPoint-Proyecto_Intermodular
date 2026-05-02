# BreakPoint — Definición del Proyecto
### Proyecto Intermodular · 1º DAW · Curso 2024/2025

---

## 1. ¿Qué es BreakPoint?

Portal web y sistema de gestion de un club de billar privado ficticio. Parte del proyecto intermodular de 1ª de DAW.

---

## 2. ¿Para qué sirve la web?

El portal cumple dos funciones diferenciadas:

**Cara pública:** Presenta el club, sus instalaciones y servicios al exterior. Cualquier persona puede consultar las tarifas, conocer la historia del club, ver la galería de imágenes, informarse sobre torneos activos y ponerse en contacto. Es la forma de darse a conocer frente a posibles nuevos socios y clientes.

**Gestión interna:** A través del portal, los socios pueden hacer reservas de mesas, inscribirse en torneos y consultar su información. El personal del club puede gestionar de forma digitalizada las altas de socios, el control de reservas y las plazas de torneos. Esta parte está conectada a una base de datos y, en su versión completa, se gestiona desde una aplicación Java que se desarrolla en el módulo de Programación.

---

## 3. ¿A quién va dirigida?

El portal tiene tres tipos de usuarios con necesidades distintas:

**Socios activos:** Personas que ya son miembros del club y quieren reservar una mesa, apuntarse a un torneo o consultar su información personal.

**Nuevos clientes:** Personas que no conocen el club, quieren informarse sobre qué ofrece, ver las tarifas de membresía y decidir si hacerse socios.

**Personal del club:** El equipo de BreakPoint, que necesita gestionar el día a día: altas de nuevos socios, control de reservas, inscripciones a torneos y seguimiento de pagos.

---

## 4. Servicios que ofrece el club

### Membresía de socio

Los socios tienen acceso preferente a las mesas sin coste por sesión, pueden participar en los torneos internos del club y disfrutan de descuentos en la tienda y en el taller de reparaciones.

Existen tres modalidades de pago individual:

| Plan | Precio |
|---|---|
| Mensual | 30 €/mes |
| Semestral | 27 €/mes |
| Anual | 24 €/mes |

Además hay packs especiales para grupos:

- **Pack Pareja:** dos miembros con precio conjunto
- **Pack Familia:** hasta 4 miembros con descuentos progresivos

### Alquiler de mesas

Las mesas pueden reservarse por tramos de tiempo. Los socios tienen acceso incluido en su cuota. Los no socios pagan 6 €/hora. Las reservas se gestionan de forma online a través del portal.

### Torneos internos

El club organiza torneos periódicos entre socios con modalidades, fechas, número de participantes e historial de resultados. Los premios varían en función del torneo y la participación, y los decide la directiva. En la web se pueden consultar los torneos activos y descargar los calendarios oficiales de competición (Circuito Gallego Absoluto, Femenino, Junior y Nacionales e Internacionales 25/26).

### Taller de reparaciones

Espacio de reparación y mantenimiento de tacos gestionado en colaboración con **Senshi Cues**, empresa especializada en fabricación y reparación de tacos de alta gama. Los socios de BreakPoint tienen acceso preferente al servicio técnico con tiempos de entrega reducidos y tarifas exclusivas.

Los servicios del taller incluyen: cambio de suela, reparación de virola, restauración de madera, ajuste de roscas y alineación, pulido y lacado, y diagnóstico gratuito previo a cualquier intervención.

El taller es accesible para cualquier usuario: socios, no socios y clientes externos al club.

### Tienda de accesorios

Venta de todo tipo de material necesario para los jugadores con descuentos para socios.


### Terraza

Terraza con vistas únicas a las Islas Cíes y un ambiente espectacular(especialmente en verano).

---

## 5. ¿Qué problema resuelve?

Sin este sistema, BreakPoint gestionaría todo de forma manual: llamadas por teléfono para reservas, listas en papel, sin control en tiempo real de qué mesas están libres ni quién tiene la cuota al día.

El portal y su base de datos digitalizan toda esa gestión:

- Elimina el papel en la gestión de socios, reservas y torneos
- Evita conflictos de horario mostrando la disponibilidad real de las mesas
- Centraliza la información de clientes, pagos y reservas en una base de datos estructurada
- Permite al personal del club acceder a la información desde cualquier dispositivo
- Da al club una imagen profesional y moderna, acorde a su ubicación y filosofía

> **Nota:** La aplicación Java gestiona el núcleo operativo del club (usuarios, socios, cuotas, mesas, reservas, torneos e inscripciones). Servicios como la tienda y el taller se gestionan de forma presencial y no están incluidos en la aplicación, ya que su naturaleza no requiere digitalización en esta fase del proyecto.

---

## 6. Tecnologías del proyecto

| Módulo | Tecnología | Uso |
|---|---|---|
| Lenguajes de Marcas | HTML5 + CSS3 | Maquetación y diseño del portal |
| Bases de Datos | MariaDB (via XAMPP) | Almacenamiento y gestión de datos |
| Programación | Java + JDBC | Aplicación de gestión interna (en desarrollo) |
| Entornos de Desarrollo | Git / GitHub | Control de versiones del proyecto |
| Sistemas Informáticos | XAMPP | Servidor local de desarrollo |

---

## 7. Estructura del portal web

| Página | Ruta | Contenido |
|---|---|---|
| Inicio | `index.html` | Presentación del club, reseñas y calendarios de torneos |
| Conócenos | `conocenos.html` | Historia del fundador, origen del nombre y descripción del local |
| Hazte socio | `servicios/socio.html` | Planes de membresía y packs especiales |
| Taller | `servicios/taller.html` | Servicios del taller y colaboración con Senshi Cues |
| Tienda | `servicios/tienda.html` | Catálogo de productos de la tienda presencial |
| Torneos | `torneos.html` | Torneos activos e historial con fotos |
| Galería | `galeria.html` | Imágenes del local, torneos y ambiente |
| Contacto | `contacto.html` | Horario, redes sociales, ubicación y formulario de contacto |

---

## 8. Módulos del Proyecto Intermodular que cubre

| Módulo | Código | Entregable principal |
|---|---|---|
| Lenguajes de Marcas | 0373 | Portal web completo (HTML + CSS) |
| Bases de Datos | 0484 | BD `breakpoint` + scripts SQL + documentación |
| Programación | 0485 | Aplicación Java con JDBC (CRUD completo) |
| MPO – Ampliación de Programación | — | Arquitectura MVC, herencia, `SchemaBBDD` |
| Entornos de Desarrollo | 0487 | Repositorio GitHub con historial de commits y README |
| Sistemas Informáticos | 0483 | Informe técnico del entorno de ejecución y capturas de pantalla |

---

## Autor

    Yago Montenegro Díaz-Flores
Proyecto desarrollado como trabajo de fin de curso 2025/2026 – 1º DAW  

