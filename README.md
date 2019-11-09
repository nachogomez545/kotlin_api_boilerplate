#Boilerplate

Este repositorio, tiene el fin de servir de template para crear nuevos proyectos REST en kotlin, para ello se utiliza lo siguiente:

- Spring boot
- Hibernate
- RxJava2
- MySQL
- maven
- JWT

####Properties
En el arcvhivo **application.yml** se encuentran ademas de las properties de configuracion, tambien se observan las rutas de los endpoints que hacen vida en la aplicacion; por ejemplo:

_login_
- /v1/auth/oauth/login
- /v1/auth/oauth/signup

_users_
- /v1/users/
- /v1/users/{id}

####Request
Todos los campos de entrada y salida estaran firmados en notacion **snake_case** como por ejemplo:

`{ user_name: "user", password: "user"}`

####Base de datos
El motor de base de datos de la aplicacion esta configurado para trabajar con **MySQL**, sin embargo utilizando **Hibernate** como ORM, esto es totalmente transparente para el proyecto, pudiendo en un futuro ser cambiado por otro SMBD sin alterar la funcionalidad del mismo. Para el correcto funcionamiento debe crearse la base de datos que lleva por nombre: **rest_api**

####Levantar el servidor

mvn clean spring-boot:run

####Probando endpoints

`curl -H "Content-Type: application/json" -d "{\"user_name\":\"user\", \"password\":\"user\"}" -X POST http://localhost:8080/v1/auh/oauth/login`