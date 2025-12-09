🛍️ Backend API – Gestión de Productos, Clientes, Ventas y Proveedores


API REST desarrollada en Java + Spring Boot, que permite gestionar productos, clientes, ventas y proveedores de un bazar/comercio.
Incluye CRUD completo, paginación y ordenamiento, validaciones, y endpoints listos para ser consumidos desde Postman o un frontend (React/Angular/etc).

✨ Features

📦 Productos: CRUD, paginación, orden por nombre ASC/DESC, validaciones.

👥 Clientes: CRUD, relación con ventas.

💰 Ventas: CRUD, relación con clientes y productos, métricas (promedios diarios/mensuales, cliente con más compras).

🏭 Proveedores: CRUD, validaciones, paginación y orden por razón social.

✅ Validaciones: @NotBlank, @Email, @Size, lógica de negocio en el service.

🔒 Preparado para integrar JWT + roles (ADMIN, EMPLEADO, CLIENTE).

🌍 Soporte para CORS, ideal para conectar con frontend.

🛠️ Tech Stack

Lenguaje: Java 21

Framework: Spring Boot 3.2

Persistencia: Spring Data JPA + MySQL

Build Tool: Maven

Testing: Postman (colecciones incluidas)

Extras: DTOs, Services, Controllers, Repository pattern

⚙️ Instalación y Configuración

Clonar el repositorio:
git clone https://github.com/tuusuario/backend-bazar.git
cd backend-bazar

Configurar la base de datos MySQL en application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/bazar_db
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Compilar y ejecutar con Maven:
mvn spring-boot:run

El backend corre en:
http://localhost:8080

Endpoints Principales
🔹 Productos

POST /productos/crear → Crear producto

GET /productos → Listar productos

GET /productos/{id} → Buscar por ID

PUT /productos/editar/{id} → Editar producto

DELETE /productos/eliminar/{id} → Eliminar producto

GET /productos/pageable?size=5&page=0 → Paginación

GET /productos/pageable/ordername/asc → Orden ascendente por nombre

🔹 Clientes

POST /clientes/crear → Crear cliente

GET /clientes → Listar clientes

GET /clientes/{id} → Buscar cliente por ID

PUT /clientes/editar/{id} → Editar cliente

DELETE /clientes/eliminar/{id} → Eliminar cliente

🔹 Ventas

POST /ventas/crear → Registrar venta

GET /ventas → Listar ventas

GET /ventas/{id} → Detalle de venta

GET /ventas/promedio-diario → Promedio de ventas por día

GET /ventas/promedio-mensual → Promedio de ventas por mes

GET /ventas/mejor-cliente-mes → Cliente con mayor compra del mes

🔹 Proveedores

POST /proveedores/crear → Crear proveedor

GET /proveedores → Listar proveedores

GET /proveedores/{id} → Buscar proveedor por ID

PUT /proveedores/editar/{id} → Editar proveedor

DELETE /proveedores/eliminar/{id} → Eliminar proveedor

GET /proveedores/pageable?sort=razonSocial,asc → Paginación + orden


Dentro de ->  src/main/resources/Scripts 
Estan los script para insertar en el Brench para poder probar por Postman los endpoints.



🔑 Autenticación JWT + roles (ADMIN, EMPLEADO, CLIENTE)
🌐 Deploy en Railway/Render para consumo directo desde frontend


💡 Autor: Ezequiel Siñeriz

## 📩 Contacto:
 

- **LinkedIn**: [Braian Ezequiel Siñeriz](https://www.linkedin.com/in/braian-ezequiel-si%C3%B1eriz-7a412b105/)  
- **Email**: [ezequielsineriz@gmail.com](mailto:ezequielsineriz@gmail.com)


```mermaid
classDiagram
    Cliente "1" --> "0..*" Venta : tiene
    Venta "1" --> "0..*" Producto : contiene
    Venta "*" --> "1" Cliente : pertenece
    Proveedor "1" --> "0..*" Producto : suministra

    class Cliente {
        +Long id
        +String nombre
        +String apellido
        +String dni
        +String metodoPago
    }

    class Producto {
        +Long id
        +String nombre
        +String marca
        +Double precio
        +Integer stock
        +LocalDateTime fechaCreacion
        +LocalDateTime fechaActualizacion
        +String mensaje
    }

    class Venta {
        +Long codigoVenta
        +LocalDate fechaVenta
        +Double total
        +List~Producto~ listaProductos
        +Cliente unCliente
    }

    class Proveedor {
        +Long id
        +String razonSocial
        +String direccion
        +String telefono
        +String cuitRuc
        +String email
        +String tipoDeProductos
        +String metodoPago
        +String diasDeEntrega
        +String plazosDePago
    }

