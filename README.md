# BreakPoint — Club de Billar

Portal web corporativo del club de billar privado **BreakPoint**, desarrollado como Proyecto Intermodular de 1º de DAW en Prometeo by The Power.

---

## ¿Qué es este proyecto?

BreakPoint es un club de billar privado ficticio. Este repositorio contiene el desarrollo completo de su portal web corporativo, incluyendo la base de datos, la aplicación de gestión interna, la documentación técnica del sistema y la web pública del club.

El proyecto integra todos los módulos de 1º de DAW en torno a una empresa ficticia de la forma más real y coherente.

## ¿Qué problema resuelve?

Sin esta solución, el club gestionaría todo manualmente: llamadas para reservas, apuntes en papel, sin control de disponibilidad de mesas ni registro de socios.

BreakPoint lo digitaliza todo:

- Elimina la gestión en papel de socios, reservas y torneos
- Evita conflictos de horario mostrando disponibilidad real de mesas
- Centraliza la información de clientes, pagos y reservas en una base de datos
- Da imagen profesional al club frente a competidores

## ¿A quién va dirigido?

- **Socios actuales** que quieren reservar mesa, apuntarse a torneos o consultar su información
- **Nuevos clientes** que quieren conocer el club, ver tarifas y hacerse socios
- **Personal del club** que necesita gestionar el día a día (altas, reservas, inscripciones...)

## Servicios que ofrece

- **Alquiler de mesas** — reserva por tramos horarios (gratuito para socios, €/h para no socios)
- **Alta como socio** — acceso preferente, descuentos en tienda y participación en torneos
- **Torneos internos** — organización de competiciones periódicas entre socios
- **Tienda de accesorios** — guantes, tizas, suelas, productos de mantenimiento
- **Zona multimedia** — retransmisiones en vivo de campeonatos oficiales

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| **HTML5 + CSS3** | Portal web corporativo |
| **Java** | Aplicación de gestión interna |
| **JDBC** | Conexión de la aplicación con la base de datos |
| **MySQL** | Base de datos relacional |
| **Git + GitHub** | Control de versiones y entrega del proyecto |

## Estructura del repositorio

```
BreakPoint/
├── diagrams/          # Diagramas E/R y otros diagramas del proyecto
├── docs/
│   ├── bbdd/          # Documentación del módulo de Bases de Datos
│   ├── general/       # Documentación general del proyecto
│   └── sistemas/      # Informe técnico de Sistemas Informáticos
├── sql/               # Scripts SQL (creación de tablas, datos, consultas)
├── src/               # Código fuente Java (aplicación de gestión)
├── web/
│   └── assets/        # Recursos estáticos (imágenes, iconos, fuentes)
└── README.md
```

## Módulos del Proyecto Intermodular

| Módulo | Contenido |
|---|---|
| **0484 – Bases de Datos** | Análisis de datos, diagrama E/R, modelo relacional, scripts SQL y consultas |
| **0487 – Entornos de Desarrollo** | Repositorio GitHub, historial de commits, documentación |
| **0373 – Lenguajes de Marcas** | Portal web en HTML + CSS (cara pública del club) |
| **0485 – Programación** | Aplicación Java con conexión JDBC para gestión interna |
| **0483 – Sistemas Informáticos** | Informe técnico del entorno de ejecución |
| **CMO – Ampliación de Programación** | Mejora estructural con POO y separación de responsabilidades |

## Cómo visualizar la web

1. Clona el repositorio
2. Abre el archivo `web/index.html` en tu navegador

No requiere servidor ni instalación adicional para la parte frontend.

## Cómo ejecutar la aplicación Java

> Próximamente — se detallará en `docs/general/` conforme avance el desarrollo.

Requisitos previstos: JDK 17+, MySQL/MariaDB, conector JDBC.

## Estado del proyecto

En desarrollo — Proyecto Intermodular 1º DAW (marzo–mayo 2026)

---

*Proyecto ficticio desarrollado con fines educativos para el ciclo formativo de Técnico Superior en Desarrollo de Aplicaciones Web.*
