# CVDS G3 - Mod #2 Salas y Prestamo
**Módulo No.2:** Gestión de Salas y Préstamo de Elementos Recreativos

## Equipo de Desarrollo

### Roles
- **Lider Técnico: 1**
- **Back: 1**
- **DevOps 1**
- **Front: 2**

### Squad Apolo
- **Dev #1:** [Nicolas Pachon](https://github.com/cedab23)    
- **Dev #2:** [Sergio Silva](https://github.com/OneCode182)
- **Dev #3:** [Santiago Amador](https://github.com/santiago-amador/)
- **Dev #4:** [Juan Lozano](https://github.com/juanLozano-2004/)
- **Dev #5:** [David Sarria](https://github.com/DASarria)



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
- **Framework Backend:** SpringBoot 3.4.x con Maven 3.x.x
- **Base de Datos:** MongoDB Atlas Cloud Cluster
- **DevOps:** Azure y GitHub Actions
- **Entorno de Desarrollo**: IntelliJ IDEA
- **Test de Endpoints:** Postman
- **Diagramas:** Astah y [Miro](https://miro.com/es/)


## Scaffolding

#### Parámetros
```yml
Java Version: 17
Spring Boot Version: 3.4.5
Grupo: eci.cvds 
Artefacto: mod2 
Paquete: eci.cvds.mod2
```

#### Dependencias Base

- **1) Spring Data MongoDB (NOSQL):** Facilita la integración con MongoDB y proporciona un repositorio para operaciones CRUD y consultas avanzadas, mapeando documentos a objetos Java.

- **2) Lombok (DEVTOOLS):** Genera automáticamente código repetitivo (getters, setters, constructores, etc.) mediante anotaciones, simplificando el mantenimiento del código.

- **3) Spring Boot DevTools (DEVTOOLS):** Ofrece reinicio automático de la aplicación, depuración mejorada y herramientas como LiveReload para facilitar el desarrollo.

- **4) Spring Web (WEB):** Permite desarrollar aplicaciones web y servicios RESTful con soporte para controladores, plantillas y manejo de rutas HTTP.

- **5) Rest Repositories (WEB):** Expone repositorios de Spring Data como servicios RESTful automáticamente, permitiendo operaciones CRUD sin escribir controladores adicionales.

- **6) Pruebas TDD (TESTING):** Framework de pruebas unitarias flexible y modular, con soporte para pruebas aisladas, parametrizadas, paralelas y extensiones.


#### Dependencias Adicionales
- **1) JUnit 5 (TESTING):** Framework de pruebas unitarias flexible y modular, con soporte para pruebas aisladas, parametrizadas, paralelas y extensiones.

- **2) JaCoCo (TESTING):** Herramienta para análisis de cobertura de pruebas en Java, proporcionando informes detallados sobre qué partes del código han sido cubiertas durante las pruebas.



## Base de Datos: MongoDB

#### Credenciales Usuario
- **Usuario:** dev
- **Contraseña:** dev123


#### Conexión desde Backend
Configurado desde `application.properties` que se encuentra en `/src/main/resources/` del proyecto. El String de Conexión desde Java a la DB es:

```str
mongodb+srv://dev:dev123@dev.okcw6.mongodb.net/Dev?retryWrites=true&w=majority
```


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


## Glosario de Comandos

| **Comando**                      | **Descripción**                                                                                           |
|-----------------------------------|-----------------------------------------------------------------------------------------------------------|
| `mvn spring-boot:run`             | Ejecuta la aplicación Spring Boot.                                                                         |
| `mvn clean`                       | Limpia el proyecto eliminando archivos generados anteriormente.                                            |
| `mvn compile`                     | Compila el código fuente del proyecto.                                                                     |
| `mvn package`                     | Compila y empaqueta el proyecto generando el archivo JAR o WAR en el directorio `target`.                  |
| `mvn clean package`               | Limpia el proyecto, lo compila y empaqueta en un solo paso.                                                |
| `mvn install`                     | Descarga las dependencias y las instala en el repositorio local. También genera el artefacto del proyecto.  |
| `mvn verify`                      | Ejecuta las pruebas y verifica si el proyecto está listo para ser desplegado.                             |
| `mvn test`                        | Ejecuta las pruebas unitarias definidas en el proyecto.                                                   |
| `mvn clean install -U`            | Limpia el proyecto, descarga dependencias y actualiza todas a las versiones más recientes.                 |
| `mvn dependency:tree`             | Muestra el árbol de dependencias del proyecto.                                                            |
| `mvn jacoco:report`               | Genera un informe de cobertura de pruebas si estás utilizando JaCoCo.                                      |
| `mvn dependency:resolve`          | Muestra todas las dependencias que se resuelven en el proyecto.                                           |



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
