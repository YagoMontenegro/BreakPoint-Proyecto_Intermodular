# BreakPoint – Portal Web del Club de Billar

## ¿Qué es este proyecto?

BreakPoint es el portal web de un club de billar ficticio. La idea era crear una web completa para gestionar la presencia online del club, donde los usuarios puedan informarse de los servicios, apuntarse como socios, consultar torneos y contactar con el club.

Este portal web forma parte del Proyecto Intermodular de 1º de DAW, y conecta los módulos de Lenguajes de Marcas, Bases de Datos, Programación, Entornos de Desarrollo y Sistemas Informáticos.

---

## ¿Qué problema resuelve?

Un club de billar real necesita una forma de darse a conocer, gestionar socios y organizar torneos. Hasta ahora todo eso se hacía de forma manual (llamadas, papel, boca a boca). Esta web centraliza esa información y sienta las bases para una futura gestión digital de reservas y socios.

---

## Tecnologías usadas

- **HTML5** y **CSS3** para la maquetación y diseño del portal
- **XAMPP** (Apache + MariaDB + phpMyAdmin) para el servidor local y la base de datos del club
- **Java** con **JDBC** para la aplicación de gestión (en desarrollo)
- **Git / GitHub** para el control de versiones

---

## Páginas del portal

| Página | Descripción |
|--------|-------------|
| `index.html` | Página principal |
| `conocenos.html` | Historia del club |
| `torneos.html` | Información sobre torneos activos y pasados |
| `galeria.html` | Galería de imágenes del equipo, torneos y ambiente |
| `contacto.html` | Formulario de contacto y ubicación |
| `servicios/socio.html` | Información para hacerse socio |
| `servicios/taller.html` | Servicio de taller de reparaciones |
| `servicios/tienda.html` | Tienda de material de billar |

---

## Estructura del repositorio

```

BreakPoint-Proyecto_Intermodular/
├── diagrams/
│   ├── Diagrama E-R.png
│   └── Modelo relacional.png
├── docs/
│   ├── bbdd/
│   │   └── README.md
│   ├── general/
│   │   ├── DefinicionProyecto.md
│   │   ├── Flujo de trabajo.PNG
│   │   └── Inicio del flujo de trabajo.PNG
│   └── sistemas/
│       └── informe_tecnico.md
├── sql/
│   ├── create_tables.sql
│   ├── insert_data.sql
│   └── queries.sql
├── src/
│   └── (en desarrollo)
├── web/
│   ├── assets/
│   │   ├── img/
│   │   └── video/
│   ├── css/
│   │   └── style.css
│   ├── servicios/
│   │   ├── socio.html
│   │   ├── taller.html
│   │   └── tienda.html
│   ├── conocenos.html
│   ├── contacto.html
│   ├── galeria.html
│   ├── index.html
│   └── torneos.html
└── README.md
```

---

## Base de datos

La base de datos se llama `breakpoint` y gestiona las siguientes entidades:

- **usuarios** – personas registradas en el club
- **socios** – usuarios que tienen membresía activa
- **cuotas_socios** – control de pagos mensuales
- **mesas** – mesas de billar disponibles
- **reservas** – reservas de mesas por usuarios/socios
- **torneos** – torneos organizados por el club
- **inscripciones** – participación de socios en torneos

Los archivos SQL para la creación de la base de datos, inserción de datos y consultas de ejemplo, están en la carpeta `/sql/`.

---

## Instrucciones de instalación

### Web (HTML + CSS)

No requiere instalación. Se puede abrir directamente en el navegador o desplegar en cualquier servidor web estático.

Con VS Code y la extensión Live Server:
1. Abrir la carpeta del proyecto
2. Click derecho sobre `index.html` → "Open with Live Server"

### Base de datos

Requisitos: XAMPP instalado y en ejecución (módulos Apache y MySQL en estado "Running").

1. Abrir el panel de control de XAMPP y arrancar Apache y MySQL
2. Acceder a `http://localhost/phpmyadmin` desde el navegador
3. En la pestaña **SQL**, pegar el contenido de `sql/create_tables.sql` y ejecutarlo
4. Repetir con `sql/insert_data.sql` para cargar los datos de prueba

O bien desde la consola de XAMPP (Shell):

```bash
mysql -u root breakpoint < sql/create_tables.sql
mysql -u root breakpoint < sql/insert_data.sql
```

### Aplicación Java (en desarrollo)

> Pendiente de implementación. Se añadirá cuando esté lista la parte de programación con JDBC.

---

## Estado del proyecto

- [x] Diseño y maquetación web (HTML + CSS)
- [x] Base de datos diseñada y creada
- [x] Datos de prueba insertados
- [x] Consultas SQL funcionales
- [ ] Aplicación Java con JDBC (en desarrollo)

---

## Autor

Proyecto desarrollado como trabajo de fin de módulo – 1º DAW  
Curso 2024/2025
