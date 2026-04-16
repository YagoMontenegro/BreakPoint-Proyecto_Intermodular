# BreakPoint — Club de Billar

> Proyecto Intermodular de 1º de DAW · Prometeo by The Power

---

## ¿Qué es este proyecto?

**BreakPoint** es el portal web corporativo y sistema de gestión de un club privado de billar ficticio.
El proyecto combina una web informativa con una aplicación de gestión interna, conectada a una base de datos real, para digitalizar el funcionamiento del club.

Este proyecto ha sido desarrollado como **Proyecto Intermodular de 1º de DAW**, integrando los conocimientos de los módulos de Bases de Datos, Programación (y MPO), Lenguaje de Marcas, Sistemas Informáticos y Entornos de Desarrollo.

---

## La empresa ficticia

**BreakPoint, Club de Billar** es un club privado que ofrece los siguientes servicios:

- **Alquiler de mesas por horas** — reserva online con precios diferenciados para socios y no socios
- **Torneos internos** — competiciones periódicas entre socios con historial de resultados
- **Tienda de accesorios** — venta de material básico 
- **Zona Chill out** - Espacio reservado para descanso entre sesiones o visualización de campeonatos oficiales en streaming.

---

## ¿Qué problema resuelve?

Sin esta solución, el club gestionaría todo de forma manual: reservas por teléfono, apuntes en papel y sin control de disponibilidad de mesas. BreakPoint lo digitaliza:

- Elimina la gestión en papel de socios y reservas
- Evita conflictos de horario mostrando disponibilidad real de mesas
- Centraliza la información de clientes, pagos y reservas en una base de datos
- Da imagen profesional al club

---

## A quién va dirigido

| Perfil | Uso |
|---|---|
| **Socios actuales** | Consultar su información, reservar mesas sin coste y acceso a torneos |
| **Nuevos clientes** | Conocer el club, tarifas y hacerse socios |
| **Personal del club** | Gestionar altas, reservas y ventas |

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| **HTML5 + CSS3** | Portal web corporativo |
| **Java** | Aplicación de gestión interna |
| **JDBC** | Conexión de la aplicación con la base de datos |
| **MySQL** | Base de datos relacional |
| **Git + GitHub** | Control de versiones y entrega del proyecto |

---

## Estructura del repositorio

```
breakpoint-club/
│
├── web/                  → Portal web (HTML + CSS)
│   ├── index.html
│   ├── css/
│   └── assets/
│
├── src/                  → Código fuente Java
│   └── ...
│
├── sql/                  → Scripts de base de datos
│   ├── create_tables.sql
│   ├── insert_data.sql
│   └── queries.sql
│
├── docs/                 → Documentación del proyecto
│   ├── sistemas/         → Informe técnico (Sistemas Informáticos)
│   ├── diagrams/         → Diagrama E/R y modelo relacional
│	├── bbdd
│
└── README.md
```

---

## Cómo ejecutar el proyecto

### Portal web
Abrir el archivo `web/index.html` directamente en cualquier navegador. No requiere servidor.

### Aplicación Java
```bash
# Requisitos: Java 17+, MySQL 8+
# 1. Importar la base de datos
mysql -u root -p < sql/create_tables.sql
mysql -u root -p < sql/insert_data.sql

# 2. Configurar la conexión en src/config/DBConnection.java
# 3. Compilar y ejecutar desde el IDE (IntelliJ IDEA o Eclipse)
```

---

## Módulos del proyecto

| Módulo | Entregable |
|---|---|
| **Bases de Datos (0484)** | Diagrama E/R, modelo relacional, scripts SQL, consultas |
| **Entornos de Desarrollo (0487)** | Repositorio GitHub, commits, README |
| **Lenguajes de Marcas (0373)** | Portal web en HTML + CSS |
| **Programación (0485)** | Aplicación Java con JDBC y operaciones CRUD |
| **Sistemas Informáticos (0483)** | Informe técnico del entorno de ejecución |
| **MPO Ampliación de Programación** | Diseño POO, arquitectura por capas, mejora estructural |

---

## Autor

**[Yago Montenegro Díaz-Flores]**  
Estudiante de 1º de DAW · Prometeo by The Power  
[https://github.com/YagoMontenegro/BreakPoint-Proyecto_Intermodular]

---

*Proyecto desarrollado entre abril y mayo de 2026.*

