# Author Service

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[Spring Boot](http://projects.spring.io/spring-boot/) based app.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.tcs.training.orderProductMap.DiscoveryService` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Local Application Urls

### Base Url

http://localhost:8889/authors

### Swagger UI

http://localhost:8889/swagger-ui/index.html

## Application endpoints:

* Retrieve all authors: ```GET /api/authors```
* Get an user by ID: ```GET /api/authors/{id}```
* Add a new user: ```POST /api/authors```
* Update an user: ```PUT /api/authors/{id}```
* Delete an user: ```DELETE /api/authors/{id}```

## API Test Client

## Copyright

Released under the Apache License 2.0. See
the [LICENSE](https://github.com/arghyagiri/microservice-e2/blob/main/LICENSE) file.
