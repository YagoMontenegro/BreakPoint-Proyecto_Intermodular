# Informe Técnico del Entorno de Ejecución  
## Proyecto BreakPoint – Club de Billar  
### Módulo: Sistemas Informáticos (0483)

---

## 1. Tipo de sistema donde se ejecuta

El proyecto BreakPoint está compuesto por dos partes: el portal web estático (HTML + CSS) y la aplicación de gestión con base de datos (Java + MySQL + JDBC).

Para el desarrollo y las pruebas, todo se ejecuta en un **PC de usuario** estándar. No hace falta ningún servidor externo ni nada especial, lo que facilita bastante el despliegue.

Dicho esto, si en el futuro se quisiese desplegar la web para que la pueda visitar cualquiera, habría que subirla a un servidor. Por ahora, el entorno de ejecución es local.

**Justificación:** Es un proyecto académico y de tamaño reducido. No tiene sentido montar un servidor dedicado. Con un PC normal y un servidor local (XAMPP o similar) es más que suficiente para desarrollar y probar todo.

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
Es el sistema que tengo instalado y con el que me siento más cómodo. Además, todas las herramientas que uso (VS Code, IntelliJ IDEA, MySQL Workbench, XAMPP) tienen versión para Windows y la instalación es bastante sencilla.

El proyecto también sería compatible con **macOS** y con distribuciones **Linux** como Ubuntu, ya que todas las tecnologías usadas son multiplataforma. Solo habría que ajustar alguna ruta en la configuración.

---

## 4. Guía de instalación

A continuación se explica cómo preparar el entorno para poder ejecutar el proyecto.

### 4.1 Herramientas necesarias

- **Visual Studio Code** – para editar el HTML y el CSS  
  Descarga: https://code.visualstudio.com/
- **Extensión Live Server** (dentro de VS Code) – para ver la web en el navegador con recarga automática
- **MySQL 8.x** – para la base de datos  
  Descarga: https://dev.mysql.com/downloads/mysql/
- **MySQL Workbench** (opcional pero muy útil) – interfaz gráfica para gestionar la base de datos  
  Descarga: https://dev.mysql.com/downloads/workbench/
- **JDK 17 o superior** – para ejecutar la aplicación Java  
  Descarga: https://adoptium.net/
- **IntelliJ IDEA Community** – IDE para desarrollar la parte Java  
  Descarga: https://www.jetbrains.com/idea/download/

---

### 4.2 Instalación paso a paso

**Paso 1 – Clonar el repositorio**

```bash
git clone https://github.com/YagoMontenegro/BreakPoint-Proyecto_Intermodular.git
cd BreakPoint-Proyecto_Intermodular
```

**Paso 2 – Abrir la web**

1. Abrir VS Code
2. Abrir la carpeta del proyecto: `Archivo > Abrir carpeta`
3. Click derecho en `index.html` → "Open with Live Server"
4. Se abrirá el navegador en `http://127.0.0.1:5500`

**Paso 3 – Crear la base de datos**

1. Abrir MySQL Workbench y conectarse al servidor local
2. Ir a `File > Open SQL Script` y abrir `sql/create_tables.sql`
3. Ejecutar con el botón del rayo (o Ctrl+Shift+Enter)
4. Repetir con `sql/insert_data.sql` para cargar los datos de prueba

O bien desde la terminal de MySQL:

```bash
mysql -u root -p
```
```sql
source /ruta/al/proyecto/sql/create_tables.sql;
source /ruta/al/proyecto/sql/insert_data.sql;
```

**Paso 4 – Aplicación Java (pendiente)**

> Esta parte está en desarrollo. Se actualizará el README cuando esté lista.

---

## 5. Usuarios, permisos y estructura

### Usuarios del sistema MySQL

| Usuario | Permisos | Uso |
|---|---|---|
| `root` | Todos (administrador) | Solo para desarrollo local y configuración inicial |
| `breakpoint_user` | SELECT, INSERT, UPDATE, DELETE sobre la BD `breakpoint` | Usuario de la aplicación Java |

> Por seguridad, la aplicación Java no debería conectarse con el usuario root. Se debería crear un usuario con permisos limitados solo a la base de datos del proyecto.

Ejemplo para crear el usuario limitado:
```sql
CREATE USER 'breakpoint_user'@'localhost' IDENTIFIED BY 'contraseña_segura';
GRANT SELECT, INSERT, UPDATE, DELETE ON breakpoint.* TO 'breakpoint_user'@'localhost';
FLUSH PRIVILEGES;
```

### Estructura de carpetas del proyecto

```
breakpoint/
├── index.html               ← Página principal
├── conocenos.html
├── torneos.html
├── galeria.html
├── contacto.html
├── servicios/               ← Páginas de servicios
├── css/                     ← Hojas de estilo
├── assets/                  ← Imágenes y videos del portal
├── sql/                     ← Scripts de base de datos
├── src/                     ← Código fuente Java (en desarrollo)
└── docs/sistemas/           ← Este informe y capturas
```

---

## 6. Mantenimiento básico

### ¿Qué habría que actualizar y con qué frecuencia?

| Qué | Cada cuánto | Por qué |
|---|---|---|
| Contenido de la web (torneos, noticias) | Según necesidad | Los torneos cambian, hay que actualizar la info |
| MySQL | Cada 6 meses | Para tener parches de seguridad |
| JDK | Cada vez que salga LTS nueva | Mantener soporte y mejoras |
| Contraseñas de acceso a la BD | Cada 3-6 meses | Buena práctica de seguridad |

### Copias de seguridad

Se recomienda hacer una copia de seguridad de la base de datos regularmente. Con MySQL es bastante sencillo:

```bash
mysqldump -u root -p breakpoint > backup_breakpoint_FECHA.sql
```

Los backups deberían guardarse fuera del directorio del proyecto (por ejemplo en una carpeta `/backups` en otra unidad o en la nube).

### ¿Qué hacer si algo falla?

- **La web no carga:** Comprobar que Live Server está activo. Revisar la consola del navegador (F12) por si hay errores en rutas de archivos.
- **La base de datos no conecta:** Verificar que el servicio MySQL está arrancado. En Windows se puede comprobar desde el Administrador de tareas o con `services.msc`.
- **Error en la aplicación Java:** Revisar los logs de la consola. Lo más habitual es un fallo en la cadena de conexión JDBC o en las credenciales.

---

## 7. Seguridad básica

Aunque es un proyecto local y académico, se han tenido en cuenta algunas medidas mínimas:

- No se sube el archivo de configuración con contraseñas al repositorio de GitHub (o al menos no debería subirse)
- El usuario de la base de datos de la aplicación tiene permisos limitados, no usa root
- Las contraseñas de la base de datos no están hardcodeadas en el código, se configuran en un archivo separado

Para un entorno de producción real habría que añadir más medidas (HTTPS, firewall, validación de entradas para evitar SQL Injection, etc.), pero para el nivel de este proyecto y siendo local, con esto es suficiente.

---

## 8. Evidencias de funcionamiento

> Las capturas de pantalla del proyecto funcionando se encuentran en la carpeta `/docs/sistemas/capturas/`.

Las evidencias incluyen:

- Captura del portal web abierto en el navegador (`localhost`)
- Captura de la base de datos creada en MySQL Workbench
- Captura de las tablas con datos de prueba
- Captura de alguna consulta SQL ejecutada correctamente

---

*Informe elaborado para el módulo Sistemas Informáticos (0483) – 1º DAW*  
*Proyecto Intermodular – Curso 2024/2025*

