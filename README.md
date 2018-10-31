
This project contains examples of how to build microservices using the following technologies:
- Circuit breaker using hystrix
- Consume services using Feign
- Contract, integration and unit tests
- Docker
- Docker Compose
- Entity/DTO conversion using Orika
- Health endpoints using Spring Actuator
- CRUD repository using JPA
- Memory database using H2
- Profiles
- OWASP dependency check
- Postman
- REST features provided by Spring Boot
- Service discovery using Consul
- Swagger documentation
- XSS validation

It is structured in these folders:
- message-service: a consumer service that allows to manage messages.
- uuid-service: a provider service that generates UUIDs used to identify the messages.
- docker-compose: allows the developers work with the services using Docker without worrying about the infrastructure or other services.


# Assumptions #

- The message content will be plain text. In case of wanting to allow HTML, etc. we would 
just have modify the validator of the class XssValidator. An alternative would have been 
to accept messages with any type of content and remove the unsafe parts before saving them.

- The messages won't have attachments or any extra information apart from the content 
(e.g. expiration date, ...). In case of wanting them we would just have to add more fields 
to MessageInputDto.

- The messages won't have PII data (personally identifiable information). In case they contain it 
we would have to encrypt the content before saving it. This is usually done with @ColumnTransformer
and the encryption functions of the database. 
An example of how to do it using the encryption functions of PostgreSQL can be found here:
https://www.thoughts-on-java.org/map-encrypted-database-columns-hibernates-columntransformer-annotation

- The message is still created in case of error calling to the UUID service (e.g. timeout), 
setting the UUID to null via a circuit breaker. The UUID can be filled later via a script 
or a scheduler that every certain time fills the missing UUIDs. An alternative would 
have been to throw an error so no message can be created without UUID. 
In case of a different error (e.g. the uuid endpoint has been renamed or deleted) 
it will throw an error.

- The service should not allow to store blank messages.


# Testing #

It includes unit, integration and contract tests. The contract ones use Spring Cloud Contract, 
that generates tests on the producer side and stubs that can be used by the consumer.


# Endpoints #

I have added a file to each project that can be imported in Postman. Each of them has 
a collection of endpoints ready to use. 

Each service has Swagger documentation that can be accessed in:
http://localhost:{port}/swagger-ui.html

The services expose health and information endpoints using Spring Actuator. An alternative 
would have been to implement a custom health endpoint for Consul and get metrics about 
the services using other tools like New Relic.


# Consul #

The services use the default port of Consul (8500), using http://localhost:8500 locally 
and http://consul:8500 on Docker. The default url to view the services in the browser is:
http://localhost:8500

1) Change general settings:

Some settings like the health check interval can be configured in the file application.yml
located in the folder src/main/resources of each project. For example:

spring:
  cloud:
    consul:
      discovery:
        healthCheckInterval: 15s

2) Change specific settings for the Docker images:

The Docker images already receive a parameter for running using the profile "docker" 
(this is configured in their Dockerfile). The settings of Consul can be changed in the file
bootstrap.yml located in the folder src/main/resources of each project. 

For example changing this:

spring:
  profiles: docker
  cloud:
    consul:
      host: consul
      port: 8700


3) Change specific settings for running without Docker:

For local we would have to run the applications adding a profile such as "local", using 
"-Dspring.profiles.active=local". We could add specific settings to the file bootstrap.yml.
For example:

---

spring:
  profiles: local
  cloud:
    consul:
      host: localhost
      port: 8700


# Compilation #

It is important that the first one is executed with install instead of package because it 
generates a stubs jar in the local Maven repository that will be used by the tests of the other.

cd uuid-service
mvn install dockerfile:build

cd ../message-service
mvn package dockerfile:build

Some dependencies (including the Spring ones) have vulnerabilities detected by the OWASP checker 
but it hasn't been released yet a safer version of them. In a real life application 
it would be needed a more thorough analysis of them before releasing the services to Prod.


# Run locally #

1) Consul:

Download the agent from https://www.consul.io/downloads.html , uncompress it, add it to the path 
and run the command:  consul agent -dev 

2) Services:

cd uuid-service
java -jar target/uuid-service-0.0.1-SNAPSHOT.jar

cd ../message-service
java -jar target/message-service-0.0.1-SNAPSHOT.jar


# Run with Docker #

1) Start the images:

The service images are configured on docker-compose.yml to depend on the image of Consul, 
so starting them will start Consul automatically on Docker.

cd docker-compose

docker-compose up -d message-service uuid-service

The services will be registered in Consul after a few seconds, you can check them here:
http://localhost:8500/ui/dc1/services


2) Stop the images:

docker-compose stop message-service uuid-service consul


3) Remove the images:

docker-compose rm message-service uuid-service consul


# To do #

There are a few things that I would like to add or change:
- Add pagination
- Fix the way of generating dates in JSON
- Monitoring using e.g. New Relic
- Add performance tests
- Remove unnecessary dependencies

Keep an eye on the project as I will add them bit by bit

