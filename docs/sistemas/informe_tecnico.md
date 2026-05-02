# Informe Técnico del Entorno de Ejecución  
## Proyecto BreakPoint – Club de Billar  
### Módulo: Sistemas Informáticos (0483)

---

## 1. Tipo de sistema donde se ejecuta

El proyecto BreakPoint está compuesto por dos partes: el portal web estático (HTML + CSS) y la aplicación de gestión con base de datos (Java + MariaDB/MySQL + JDBC).

Para el desarrollo y las pruebas, todo se ejecuta en un **PC de usuario** estándar. No hace falta ningún servidor externo ni nada especial, lo que facilita bastante el despliegue.

Dicho esto, si en el futuro se quisiese desplegar la web para que la pueda visitar cualquiera, habría que subirla a un servidor. Por ahora, el entorno de ejecución es local.

**Justificación:** Es un proyecto académico y de tamaño reducido. No tiene sentido montar un servidor dedicado. Con un PC normal y XAMPP (que incluye Apache y MariaDB/MySQL) es más que suficiente para desarrollar y probar todo.

---

## 2. Requisitos de hardware

### Mínimos (para que funcione)

| Componente | Mínimo |
|---|---|
| CPU | Intel Core i3 o equivalente (2 núcleos) |
| RAM | 4 GB |
| Almacenamiento | 10 GB libres |
| Pantalla | Resolución 1280×720 |
| Conexión a Internet | Sí (para cargar fuentes de Google Fonts o enlaces externos) |

### Recomendados (para trabajar cómodo)

| Componente | Recomendado |
|---|---|
| CPU | Intel Core i5 o equivalente (4 núcleos) |
| RAM | 8 GB o más |
| Almacenamiento | 20 GB libres (con espacio para IDE, JDK, MySQL…) |
| Pantalla | Resolución 1920×1080 |

> En mi caso personal lo he desarrollado en un sobremesa con 16 GB de RAM e Intel Core i7.

---

## 3. Sistema operativo

**Sistema operativo utilizado para el desarrollo:** Windows 10 Pro (64 bits)

**¿Por qué Windows?**  
Es el sistema que tengo instalado y con el que me siento más cómodo. Además, todas las herramientas que uso (VS Code, IntelliJ IDEA, XAMPP) tienen versión para Windows y la instalación es bastante sencilla.

El proyecto también sería compatible con **macOS** y con distribuciones **Linux** como Ubuntu, ya que todas las tecnologías usadas son multiplataforma. Solo habría que ajustar alguna ruta en la configuración.

---

## 4. Guía de instalación

A continuación se explica cómo preparar el entorno para poder ejecutar el proyecto.

### 4.1 Herramientas necesarias

- **XAMPP** – incluye Apache (servidor web) y MariaDB/MySQL (base de datos) con phpMyAdmin como interfaz gráfica de administración  
  Descarga: https://www.apachefriends.org/
- **Visual Studio Code** – para editar el HTML y el CSS  
  Descarga: https://code.visualstudio.com/
- **Extensión Live Server** (dentro de VS Code) – para ver la web en el navegador con recarga automática
- **JDK 17 o superior** – para ejecutar la aplicación Java  
  Descarga: https://adoptium.net/
- **IntelliJ IDEA Community** – IDE para desarrollar y ejecutar la parte Java  
  Descarga: https://www.jetbrains.com/idea/download/

---

### 4.2 Instalación paso a paso

**Paso 1 – Instalar XAMPP**

1. Descargar XAMPP desde https://www.apachefriends.org/
2. Ejecutar el instalador y seguir el asistente (los componentes por defecto son suficientes)
3. Al finalizar, abrir el panel de control de XAMPP
4. Arrancar los módulos **Apache** y **MySQL** (deben quedar en estado "Running")

**Paso 2 – Clonar el repositorio**

```bash
git clone https://github.com/YagoMontenegro/BreakPoint-Proyecto_Intermodular.git
cd BreakPoint-Proyecto_Intermodular
```

**Paso 3 – Abrir la web**

1. Abrir VS Code
2. Abrir la carpeta del proyecto: `Archivo > Abrir carpeta`
3. Click derecho en `web/index.html` → "Open with Live Server"
4. Se abrirá el navegador en `http://127.0.0.1:5500`

**Paso 4 – Crear la base de datos**

1. Con XAMPP en ejecución (Apache y MySQL arrancados), abrir el navegador
2. Acceder a `http://localhost/phpmyadmin`
3. En la pestaña **SQL**, pegar el contenido de `sql/create_tables.sql` y ejecutarlo
4. Repetir con `sql/insert_data.sql` para cargar los datos de prueba

O bien desde la consola de XAMPP (botón "Shell" en el panel de control):

```bash
mysql -u root
```
```sql
source C:/ruta/al/proyecto/sql/create_tables.sql;
source C:/ruta/al/proyecto/sql/insert_data.sql;
```

**Paso 5 – Ejecutar la aplicación Java**

1. Abrir IntelliJ IDEA
2. Importar el proyecto como proyecto Maven (`File > Open` → seleccionar la carpeta del proyecto)
3. Esperar a que Maven descargue las dependencias (MySQL Connector/J)
4. Asegurarse de que XAMPP tiene MySQL arrancado
5. Ejecutar `Main.java`
6. Interactuar con el menú de gestión en la consola

---

## 5. Usuarios, permisos y estructura

### Usuarios del sistema MySQL (XAMPP)

| Usuario | Permisos | Uso |
|---|---|---|
| `root` | Todos (administrador) | Solo para desarrollo local y configuración inicial |
| `breakpoint_user` | SELECT, INSERT, UPDATE, DELETE sobre la BD `breakpoint` | Usuario de la aplicación Java |

> Por seguridad, la aplicación Java no debería conectarse con el usuario root en un entorno de producción. Se debería crear un usuario con permisos limitados solo a la base de datos del proyecto.

Ejemplo para crear el usuario limitado (ejecutar en phpMyAdmin o en la consola de XAMPP):
```sql
CREATE USER 'breakpoint_user'@'localhost' IDENTIFIED BY 'contraseña_segura';
GRANT SELECT, INSERT, UPDATE, DELETE ON breakpoint.* TO 'breakpoint_user'@'localhost';
FLUSH PRIVILEGES;
```

### Estructura de carpetas del proyecto

```
BreakPoint-Proyecto_Intermodular/
├── web/                         ← Portal web (HTML + CSS)
│   ├── index.html
│   ├── conocenos.html
│   ├── torneos.html
│   ├── galeria.html
│   ├── contacto.html
│   ├── servicios/
│   ├── css/
│   └── assets/
├── src/                         ← Código fuente Java (aplicación de gestión)
│   └── main/java/
│       ├── Main.java
│       ├── model/
│       ├── dao/
│       ├── controller/
│       ├── view/
│       ├── database/
│       └── utils/
├── sql/                         ← Scripts de base de datos
├── diagrams/                    ← Diagramas E/R y modelo relacional
├── docs/                        ← Documentación
│   ├── bbdd/
│   ├── general/
│   └── sistemas/
│       ├── informe_tecnico.md
│       └── capturas/
└── pom.xml                      ← Configuración Maven
```

### Dónde se guardan los datos

- **Base de datos:** en el directorio de datos de XAMPP (por defecto `C:\xampp\mysql\data\breakpoint\`)
- **Copias de seguridad:** se recomienda guardarlas fuera del directorio del proyecto, en otra unidad o en la nube

---

## 6. Mantenimiento básico

### ¿Qué habría que actualizar y con qué frecuencia?

| Qué | Cada cuánto | Por qué |
|---|---|---|
| Contenido de la web (torneos, noticias) | Según necesidad | Los torneos cambian, hay que actualizar la info |
| XAMPP (Apache + MySQL) | Cada 6 meses | Para tener parches de seguridad |
| JDK | Cada vez que salga LTS nueva | Mantener soporte y mejoras |
| Contraseñas de acceso a la BD | Cada 3-6 meses | Buena práctica de seguridad |

### Copias de seguridad

Se recomienda hacer una copia de seguridad de la base de datos regularmente. Desde la consola de XAMPP (Shell):

```bash
mysqldump -u root breakpoint > backup_breakpoint_FECHA.sql
```

También se puede hacer desde phpMyAdmin: seleccionar la base de datos `breakpoint` → pestaña **Exportar** → ejecutar.

Los backups deberían guardarse fuera del directorio del proyecto (por ejemplo en una carpeta `/backups` en otra unidad o en la nube).

### ¿Qué hacer si algo falla?

- **La web no carga:** Comprobar que Live Server está activo. Revisar la consola del navegador (F12) por si hay errores en rutas de archivos.
- **La base de datos no conecta:** Abrir el panel de control de XAMPP y verificar que el módulo MySQL está arrancado (debe estar en verde). Si no arranca, comprobar que el puerto 3306 no esté ocupado por otro programa.
- **Error en la aplicación Java:** Revisar los logs de la consola. Lo más habitual es un fallo en la cadena de conexión JDBC (verificar que XAMPP tiene MySQL arrancado y que la base de datos `breakpoint` existe).
- **phpMyAdmin no abre:** Comprobar que tanto Apache como MySQL están arrancados en el panel de XAMPP.

---

## 7. Seguridad básica

Aunque es un proyecto local y académico, se han tenido en cuenta algunas medidas mínimas:

- No se sube el archivo de configuración con contraseñas al repositorio de GitHub (o al menos no debería subirse)
- Se recomienda crear un usuario de base de datos con permisos limitados (`breakpoint_user`) en lugar de usar `root`
- Para un entorno de producción real habría que añadir más medidas (HTTPS, firewall, validación de entradas para evitar SQL Injection, etc.), pero para el nivel de este proyecto y siendo local, con esto es suficiente

---

## 8. Evidencias de funcionamiento

> Las capturas de pantalla del proyecto funcionando se encuentran en la carpeta `/docs/sistemas/capturas/`.

Las evidencias incluyen:

- Captura del portal web abierto en el navegador (a través de Live Server)
- Captura del panel de control de XAMPP con Apache y MySQL arrancados
- Captura de phpMyAdmin con la base de datos `breakpoint` creada y las tablas con datos
- Captura de alguna consulta SQL ejecutada correctamente en phpMyAdmin
- Captura de la aplicación Java ejecutándose en consola (menú principal y alguna operación)

---

*Informe elaborado para el módulo Sistemas Informáticos (0483) – 1º DAW*  
*Proyecto Intermodular – Curso 2024/2025*
