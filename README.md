# BreakPoint – Portal Web y Sistema de Gestión del Club de Billar

## ¿Qué es este proyecto?

BreakPoint es el portal web y el sistema de gestión interna de un club de billar ficticio. El proyecto incluye una web completa para la presencia online del club y una aplicación Java con conexión JDBC a base de datos para la gestión de usuarios, socios, mesas, reservas, cuotas y torneos.

Este portal web forma parte del Proyecto Intermodular de 1º de DAW, y conecta los módulos de Lenguajes de Marcas, Bases de Datos, Programación, Entornos de Desarrollo y Sistemas Informáticos.

---

## ¿Qué problema resuelve?

Un club de billar real necesita una forma de darse a conocer, gestionar socios y organizar torneos. Hasta ahora todo eso se hacía de forma manual (llamadas, papel, boca a boca). Esta web centraliza la información pública del club y la aplicación Java permite gestionar digitalmente el núcleo del negocio: altas de usuarios y socios, control de cuotas, reservas de mesas, organización de torneos e inscripciones.

---

## Tecnologías usadas

- **HTML5** y **CSS3** para la maquetación y diseño del portal
- **XAMPP** (Apache + MariaDB/MySQL + phpMyAdmin) para el servidor local y la base de datos del club
- **Java 23** con **JDBC** (MySQL Connector/J 9.6.0) para la aplicación de gestión
- **Maven** para la gestión de dependencias del proyecto Java
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

## Aplicación Java — Sistema de gestión interna

La aplicación de consola permite al personal del club gestionar el día a día del negocio. Está conectada a la base de datos `breakpoint` mediante JDBC y ofrece CRUD completo sobre las siguientes entidades:

| Módulo de gestión | Funcionalidades |
|---|---|
| Usuarios | Alta, listado, búsqueda por teléfono, modificación y eliminación |
| Socios | Alta desde usuario existente, listado, búsqueda, cambio de estado y baja |
| Cuotas | Generación de cuotas, consulta por socio, registro de pago y eliminación |
| Mesas | Alta de mesas, listado, cambio de estado y eliminación |
| Reservas | Creación con cálculo automático de coste (socio: 0 €/h, no socio: 6 €/h), listado, búsqueda, modificación y cancelación |
| Torneos | Creación, listado, modificación y eliminación |
| Inscripciones | Inscripción de socios a torneos, consulta, registro de resultados y eliminación |

### Arquitectura del código (MVC)

El proyecto sigue el patrón Modelo-Vista-Controlador con separación en paquetes:

```
src/main/java/
├── Main.java                  ← Punto de entrada
├── model/                     ← Clases de dominio (POJOs)
│   ├── Usuario.java
│   ├── Socio.java             (hereda de Usuario)
│   ├── Mesa.java
│   ├── Reserva.java
│   ├── Torneo.java
│   ├── CuotaSocio.java
│   └── Inscripcion.java
├── dao/                       ← Acceso a datos (JDBC)
│   ├── UsuarioDAO.java
│   ├── SocioDAO.java
│   ├── MesaDAO.java
│   ├── ReservaDAO.java
│   ├── TorneoDAO.java
│   ├── CuotaSocioDAO.java
│   └── InscripcionDAO.java
├── controller/                ← Lógica de la aplicación
│   ├── GestionAppController.java
│   ├── UsuarioController.java
│   ├── SocioController.java
│   ├── MesaController.java
│   ├── ReservaController.java
│   ├── TorneoController.java
│   ├── CuotaSocioController.java
│   └── InscripcionController.java
├── view/                      ← Menús de consola
│   ├── MenuPrincipalView.java
│   ├── UsuarioView.java
│   ├── SocioView.java
│   ├── MesaView.java
│   ├── ReservaView.java
│   ├── TorneoView.java
│   ├── CuotaSocioView.java
│   └── InscripcionView.java
├── database/                  ← Conexión y esquema
│   ├── ConexionBBDD.java
│   └── SchemaBBDD.java
└── utils/                     ← Utilidades
    └── InputHelper.java
```

### Mejora MPO — Ampliación de Programación

La mejora estructural respecto al proyecto base de Programación ha consistido en:

- **Separación en capas MVC**: la lógica de negocio (controller), el acceso a datos (dao), la presentación (view) y el dominio (model) están en paquetes independientes con responsabilidades claras.
- **Clase `SchemaBBDD`**: interfaz que centraliza todos los nombres de tablas y columnas como constantes, evitando strings dispersos por el código y facilitando el mantenimiento.
- **Clase `InputHelper`**: utilidad que centraliza la lectura y validación de datos de entrada (texto, email, teléfono, fechas, números) para evitar duplicidad de código entre controllers.
- **Herencia `Socio extends Usuario`**: uso natural de POO que refleja la relación real del negocio (un socio es un usuario con información adicional).

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
│       ├── informe_tecnico.md
│       └── capturas/
├── sql/
│   ├── create_tables.sql
│   ├── insert_data.sql
│   └── queries.sql
├── src/
│   └── main/java/
│       ├── Main.java
│       ├── model/
│       ├── dao/
│       ├── controller/
│       ├── view/
│       ├── database/
│       └── utils/
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
├── pom.xml
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

Los archivos SQL para la creación de la base de datos, inserción de datos y consultas de ejemplo, están en la carpeta `/sql/`. La documentación detallada de la base de datos se encuentra en `/docs/bbdd/README.md`.

---

## Instrucciones de instalación

### Web (HTML + CSS)

No requiere instalación. Se puede abrir directamente en el navegador o desplegar en cualquier servidor web estático.

Con VS Code y la extensión Live Server:
1. Abrir la carpeta del proyecto
2. Click derecho sobre `web/index.html` → "Open with Live Server"

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

### Aplicación Java

Requisitos: JDK 17 o superior y Maven instalados. XAMPP con MySQL en ejecución y la base de datos `breakpoint` creada.

1. Abrir el proyecto en IntelliJ IDEA (o cualquier IDE compatible con Maven)
2. Esperar a que Maven descargue las dependencias (`mysql-connector-j`)
3. Ejecutar `Main.java`
4. Interactuar con la aplicación a través del menú de consola

---

## Estado del proyecto

- [x] Diseño y maquetación web (HTML + CSS)
- [x] Base de datos diseñada y creada
- [x] Datos de prueba insertados
- [x] Consultas SQL funcionales
- [x] Aplicación Java con JDBC finalizada
- [x] CRUD completo de todas las entidades
- [x] Arquitectura MVC implementada
- [x] Informe técnico de Sistemas Informáticos

---

## Autor

Proyecto desarrollado como trabajo de fin de módulo – 1º DAW  
Curso 2024/2025
