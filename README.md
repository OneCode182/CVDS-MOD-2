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


## Estructura del Proyecto - Scaffolding

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


#### Dependencias Adicionales
- **1) JUnit 5 (TESTING):** Framework de pruebas unitarias flexible y modular, con soporte para pruebas aisladas, parametrizadas, paralelas y extensiones.

- **2) JaCoCo (TESTING):** Herramienta para análisis de cobertura de pruebas en Java, proporcionando informes detallados sobre qué partes del código han sido cubiertas durante las pruebas.

- **3) Swagger OpenAPI (DOCS):** Para documentar la API de este proyecto, se utiliza **Swagger** a través de la biblioteca **springdoc-openapi**. Esto permite generar y visualizar la documentación de los endpoints de la API de forma interactiva.



## Base de Datos: MongoDB

#### Credenciales Usuario
- **Usuario:** dev
- **Contraseña:** Ha9ky3DYQj1LmbUs


#### Conexión desde Backend
Desde el `application.properties` que se encuentra en `/src/main/resources/` del proyecto, el String de Conexión es:


```properties
# String de Conexion Java
spring.data.mongodb.uri=mongodb+srv://dev:Ha9ky3DYQj1LmbUs@dev.spbbdmr.mongodb.net/Dev?retryWrites=true&w=majority
spring.data.mongodb.database=Dev
```

Para conectarse desde un cliente gráfico como `MongoDB Compass`:
```properties
# String de Conexion Cliente
mongodb+srv://dev:Ha9ky3DYQj1LmbUs@dev.spbbdmr.mongodb.net/
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



## Novedades
- **Estructura:** Definir la estructura (Scaffolding) del proyecto, dependencias y codigo base.

- **Dominio:** Desarrollo de las entidades base para el modulo y el API.
  
- **Despliegue e Integración:** Configuracion en Azure en el Tema de Despliegue y vinculación con el repo mediante GitHub Actions.

- **Reglas de GitHub:** Al aprovar PR de ramas `feature` o `fix` etc, y luego de hacer merge, se borran de manera automatica. Además se protege a main y a dev para que no se eliminen por esta regla o por usuario.

- **Documentación Endpoints API RESTful:** Documentacion con Swagger/OpenAPI funcional.



- **Access the API documentation:**
    * [Swagger URL](https://app.swaggerhub.com/apis-docs/DIEGOSP778/modulo-prestamos_api/1.0#/)

## Endpoints

Este microservicio expone varios endpoints RESTful. A continuación, algunos ejemplos de los endpoints disponibles:

| Método | Endpoint                              | Descripción                                                                  |
|--------|---------------------------------------|------------------------------------------------------------------------------|
| GET    | `/api/health`                         | Endpoint por defecto para verificar el estado de funcionamiento del servicio |
| GET    | `/salas`                              | Obtener todas las salas disponibles y sus detalles                           |
| GET    | `/salas/{id}`                         | Obtener los detalles de una sala específica por ID                           |
| GET    | `/salas/disponibilidad`               | Obtener la disponibilidad de todas las salas en tiempo real                  |
| GET    | `/salas/{id}/reservas`                | Obtener todas las reservas de una sala específica                           |
| GET    | `/elementos`                          | Obtener todos los elementos recreativos disponibles                         |
| GET    | `/elementos/{id}`                     | Obtener los detalles de un elemento recreativo específico por ID             |
| GET    | `/elementos/{id}/disponibilidad`      | Consultar la disponibilidad de un elemento recreativo específico             |
| POST   | `/reservas`                           | Crear una nueva reserva para una sala o elemento recreativo                  |
| POST   | `/reservas/{id}/cancelar`             | Cancelar una reserva específica por ID                                        |
| PUT    | `/reservas/{id}`                      | Actualizar una reserva existente (por ejemplo, cambiar hora o sala/elemento) |
| DELETE | `/reservas/{id}`                      | Eliminar una reserva (si se cumplen las condiciones)                         |
| GET    | `/reservas/usuario/{id}`              | Obtener todas las reservas de un usuario específico por ID                   |
| GET    | `/reservas/elemento/{id}`             | Obtener reservas por el ID de un elemento recreativo específico              |
| GET    | `/prestamos`                          | Obtener todos los préstamos de elementos recreativos                        |
| GET    | `/prestamos/{id}`                     | Obtener los detalles de un préstamo por ID                                    |
| POST   | `/prestamos`                          | Crear un nuevo préstamo de un elemento recreativo                            |
| PUT    | `/prestamos/{id}`                     | Actualizar los detalles del préstamo (por ejemplo, fecha de devolución, estado del elemento) |
| DELETE | `/prestamos/{id}`                     | Eliminar un registro de préstamo (si se cumplen las condiciones)             |
| GET    | `/prestamos/usuario/{id}`             | Obtener todos los préstamos de un usuario específico                         |
| GET    | `/prestamos/elemento/{id}`            | Obtener todos los préstamos de un elemento recreativo específico             |


***NOTA:*** *Los enpoints en formato **Swagger** están disponibles [Aqui]()*


### Configuraciones Adicionales
1. **CORS:** Configurado para permitir solo solicitudes del frontend desde la URL definida FRONTEND_URL.

2. **Configuración de Swagger:** Detalles de la API e información de contacto del equipo incluidos.



## Licencia

Este proyecto está licenciado bajo la **Licencia MIT** - consulta el archivo [LICENCE]() para más detalles.