# Prueba técnica desarrollo Devsu

El proyecto esta confortmado por 2 microservicios alojados en los repositorios que se indican a continuacion
- **Microservicio de persona** - cliente [ms-cliente-persona.git](https://github.com/deiberv/ms-cliente-persona.git). Codigo fuente en el directorio [ms-cliente-persona](./ms-cliente-persona/)
- **Microservicio cuentas** - movimientos [ms-cuenta-movimiento.git](https://github.com/deiberv/ms-cuenta-movimiento.git). Codigo fuente en el directorio [ms-cuenta-movimiento](./ms-cuenta-movimiento/)

Cada repositorio cuenta con su respectiva documentación interna, scripts de base de datos de cada uno.

## Base de datos
Se adjunta scrips de base de datos para cada microservicio, se utilizó MySql como manejador de base de datos para ambos MS.
Los scrips los podemos conseguir en el directorio **base-datos**.
- ms-cliente-persona [BaseDatos-ms-persona-cliente.sql](./base-datos/BaseDatos-ms-persona-cliente.sql).
- ms-cuenta-movimiento [BaseDatos-ms-cuenta-movimiento.sql](./base-datos/BaseDatos-ms-cuenta-movimiento.sql).
También se encuentran en los repositorios de cada microservicio con el nombre BaseDatos.sql

## Postman
Se adjuntan collecciones de postman utilizada para cada microservicio
- ms-cliente-persona [ms-clientes-devsu.postman_collection](./collection-postman/ms-clientes-devsu.postman_collection.json).
- ms-cuenta-movimiento [ms-cuenta-movimiento.postman_collection](./collection-postman/ms-cuenta-movimiento.postman_collection.json).

## Ejecutar en docker
Antes de ejecutar el proyecto en contenedores docker, es necesario primero generar los jar a ser desplegados.
Para ello en en cada proyecto de debe de ejecutar el comando maven en cada proyecto
```shell
mvn package 
```
Este comando generará los jar necesarios en el directorio devsu-docker/ms-cliente-persona y devsu-docker/ms-cuenta-movimiento respectivamente.

Se cuenta con una orquetacion de contenedores utilizando docker compose que esta ubicado en el directorio *devsu-docker* de este repositorio.

Servicios con lo que se dispone en el docker-compose.yml

- rabbitmq([Ingresar a rabbitMQ](http://localhost:15672/))
- sonarqube (Utilizado para el análisis de código)
- mysql Servidor mysql utilizado para ambos proyectos
- phpmyadmin ([Ingresar a phpmyadmin](http://localhost:9001/)) para gestionar la base de datos
- ms-cliente-persona Se ejecuta por el puerto 8081 [documentacion](http://localhost:8081/swagger-ui/index.html)
- ms-cuenta-movimiento Se ejecuta por el puerto 8082 [documentacion](http://localhost:8082/swagger-ui/index.html)

### Variables de entorno definidas en el docker-compose.yml
- **mysql**
  - MYSQL_USER: dbuser
  - MYSQL_PASSWORD: dbpass
  - MYSQL_ROOT_PASSWORD: dbpass
- **phpmyadmin**
  - PMA_HOST: mysql
  - MYSQL_ROOT_PASSWORD: dbpass
- **ms-api-gateway**
  - EUREKA_SERVER: http://localhost:8761/eureka
- **ms-cliente-persona**
  - MYSQL_SERVER_IP: mysql 
  - MYSQL_SERVER_PORT: 3306 
  - MYSQL_USER: root 
  - MYSQL_PASSWORD: dbpass 
  - RABBITMQ_HOST: rabbitmq
  - RABBITMQ_PORT: 5672 
  - RABBITMQ_USER: guest 
  - RABBITMQ_PASSWORD: guest
  - EUREKA_SERVER: http://localhost:8761/eureka
- **ms-cuenta-movimiento**
  - MYSQL_SERVER_IP: mysql 
  - MYSQL_SERVER_PORT: 3306 
  - MYSQL_USER: root 
  - RABBITMQ_HOST: rabbitmq
  - RABBITMQ_PORT: 5672 
  - RABBITMQ_USER: guest 
  - RABBITMQ_PASSWORD: guest
  - REST_CLIENTE_URL: http://localhost:8081/clientes (Comunicación sincrona con el ms-cliente-persona)
  - EUREKA_SERVER: http://localhost:8761/eureka
  
Para levantar las ejecucion de los contenedores mediante docker compose, se debe ubicar en el directorio **devsu-docker** de este repositorio y ejecutar el comando
```shell
docker compose up -d
```

## Consideraciones
Al ejecutar en docker se crea solo la base de datos SQL para el ms-cliente-persona que tiene por nombre api_clientes, se debe ejecutar el scripts de base de datos de para crear las tablas para este microservicio
[BaseDatosCliente.sql](./base-datos/BaseDatos-ms-persona-cliente.sql) y adicionalmente crear la base de datos para el ms de cuentas movimientos ejecutando el scripts de base de datos [BaseDatosMovimiento.sql](./base-datos/BaseDatos-ms-cuenta-movimiento.sql).

La ejecucion o start del ms ms-cuenta-movimiento puede fallar esto debido a la en rabbitMQ puede ser posible que no exista la cola, esto se solventaría haciendo alguna petición al ms-cliente-persona de creacion de un cliente, luego a ello se podría levantar el microservicio ms-cuenta-movimiento para consumir el evento y este se despleigue correctamente

## Pruebas
Se cuentan con un set de pruebas Unitarias de cada componente de los micro servicios.
La prueba de integracion se reallizan mediante Spring y es ejecuta con el resto de las pruebas.
Alguno de los motivos por lo que podría falla la generacion del jar o ejecucion de las pruebas se indicana acontinuacion

- No existe la base de datos para el microservicio
  Ejecutar los respectivos scripts sql proporsionados en este proyecto
- No se logra realizar conexion con la base de datos
  Revisar las credenciales de acceso, por defecto los contenedores y proyecto tienen las credenciales para ejecutar mySql en contenedor docker.
- ms-cliente-persona No se logra hacer conexion con servidor rabbitMQ
  Revisar las credenciales de acceso, por defecto los contenedores y proyecto tienen las credenciales para ejecutar rabbitmq en contenedor docker.
- ms-cuenta-movimiento No existencia de la cola **devsu.clientes.queue** en el servidor rabbitMQ
  Se puede crear la cola de manera manual ingresando al servidor de rabbitmq [rabittmq](http://localhost:15672/#/queues). El ms-cliente-persona crea la cola al ejecutar la Creación de un cliente

