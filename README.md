# CVDS G3 - Mod #2 Salas y Prestamo
**Módulo No.2:** Gestión de Salas y Préstamo de Elementos Recreativos

## Equipo de Desarrollo

### Squad Apolo
- **Lider Técnico:** [Nicolas Pachon](https://github.com/cedab23)    
- **Back & DevOps:** [Sergio Silva](https://github.com/OneCode182)
- **Back & DevOps:** [Santiago Amador](https://github.com/santiago-amador/)
- **Front:** [Juan Lozano](https://github.com/juanLozano-2004/)
- **Front:** [David Sarria](https://github.com/DASarria)



## Introducción


Este repositorio contiene un microservicio backend en **Java con Spring Boot**, parte de una arquitectura de microservicios con **API Gateway**. Gestiona la reserva de salas de descanso y recreación, así como el préstamo y devolución de elementos recreativos, dirigido a la comunidad académica para promover el bienestar universitario.


### Funcionalidades principales:
- Gestión de datos de la aplicación web.
- Autenticación y autorización de usuarios.
- Interacción con otros microservicios a través del API Gateway.
- Exposición de endpoints RESTful utilizando Spring Boot.
- Integración con bases de datos para la persistencia de la información.


## Stack de Tecnología y Herramientas
- **Lenguaje:** Java 17+
- **Framework Backend:** SpringBoot 3.4.4 con Maven 3.x.x
- **Base de Datos:** MongoDB Atlas Cloud Cluster
- **DevOps:** Azure y GitHub Actions
- **Entorno de Desarrollo**: IntelliJ IDEA
- **Test de Endpoints:** Postman
- **Diagramas:** Astah y [Miro](https://miro.com/es/)
- 


## Dependencias

- **1) Persistencia (NOSQL):** Spring Data MongoDB
- **2) Refactor (DEVTOOLS):** Lombok
- **3) Desarrollo (DEVTOOLS):** Spring Boot DevTools
- **4) Servidor Web (WEB):** Spring Web
- **5) Api (WEB):** Rest Repositories
- **6) Pruebas TDD (TESTING):** JUnit 5


## Scaffolding



## Instalación
1. Clonar este repositorio:

   ```bash
   $ git clone https://github.com/OneCode182/CVDS-MOD2
   ```

2. Entrar en el directorio:
   ```bash
   > cd CVDS-MOD2
   ```

3. Ejecutar el proyecto con Maven:
   ```bash
   > mvn spring-boot:run
   ```

4. La aplicación estará corriendo en http://localhost:8080 por defecto.

5. La documentación **Swagger** está disponible haciendo [Clic Aquí]()

## Configuración del entorno
Es posible configurar las variables en el archivo `application.properties`:

- `server.port`: Puerto en el que se ejecuta la aplicación.

- `spring.datasource.url`: URL de conexión a la base de datos.

- `spring.datasource.username`: Nombre de usuario de la base de datos.

- `spring.datasource.password`: Contraseña de la base de datos.

## Novedades
- **Loan Management**: Create, update, and delete book loans.

- **Fine Calculation**: Automatically calculates fines for overdue books.
  
- **Status Tracking**: Tracks loans by their status (`Prestado`, `Vencido`, `Devuelto`).

- **API Documentation**: Fully documented endpoints using Swagger/OpenAPI.



1. Access the API documentation:
    * [Swagger URL](https://app.swaggerhub.com/apis-docs/DIEGOSP778/modulo-prestamos_api/1.0#/)

## Endpoints
Este microservicio expone varios endpoints RESTful. A continuación, algunos ejemplos de los endpoints disponibles:

| Method | Endpoint                     | Description                                         |
|--------|------------------------------|-----------------------------------------------------|
| POST   | `/prestamos`                 | Create a new loan                                   |
| GET    | `/prestamos`                 | Retrieve all loans                                  |
| GET    | `/prestamos-prestados`       | Retrieve loans with status `Prestado`              |
| GET    | `/prestamos/{id}`            | Retrieve loan details by ID                        |
| GET    | `/prestamos/libro/{isbn}`    | Retrieve loans by book ISBN                        |
| GET    | `/prestamos/estudiante/{id}` | Retrieve loans by student ID                      |
| DELETE | `/prestamos/{id}/delete`         | Delete a loan (if conditions are met)              |

  ***NOTA:*** *Los enpoints en formato **Swagger** están disponibles [Aqui]()*


### Configuraciones Adicionales
1. **CORS:** Configurado para permitir solo solicitudes del frontend desde la URL definida FRONTEND_URL.

2. **Configuración de Swagger:** Detalles de la API e información de contacto del equipo incluidos.


¿Preguntas a tener en cuenta?

1.¿Cuales son las salas?  
2.¿Puedo modificar mis horarios por medio de un endpoint que me da el modulo de usuarios o lo hago yo y ya?  
3.¿Cuantas y cuales son las salas crea?  
4.¿Como manejamos las personas que van con la reserva?, porque si van muchos todos deben  
	registrarse, entonces registramos a todas esas personas? o simplemente dejamos a   
	el que reserva como encargado y ya, o mejor registramos a todas las personas en la reserva   
	que se quiera hacer.  
5.¿Solo se puede pedir un elemento recreacional a la vez?  


## Licencia

Este proyecto está licenciado bajo la **Licencia MIT** - consulta el archivo [LICENCE]() para más detalles.
