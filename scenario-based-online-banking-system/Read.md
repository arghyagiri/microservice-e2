# SAGA Pattern - Event Driven Architecture

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[Spring Boot](http://projects.spring.io/spring-boot/) based apps.

## Architecture Overview
### ```Inventory Service: ```
* Manages inventory
### ```Product Service: ```
* Handles CRUD operations for notifications.
### ```Order Service: ```
* Handles CRUD operations for orders. Depends on the ```Product Service``` to verify notification availability.
### ```Payment Service: ```
* Handles CRUD operations for payments.
### ```Notification Service: ```
* Handles CRUD operations for notifications.

* All service uses ```Kafka Message Broker``` to send update to other services to complete saga.

## Saga Flow:

* OrderService receives a request to place an order.
* It checks with InventoryService if the stock is available.
* If yes, InventoryService deducts the stock and emits a StockDeductedEvent.
* PaymentService listens to StockDeductedEvent, charges the payment, and emits a PaymentChargedEvent.
* NotificationService listens to PaymentChargedEvent and notifies the user.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the below classes from your IDE.

`com.tcs.training.user.InventoryApplication`
[link](./inventory-service/src/main/java/com/tcs/training/inventory/InventoryApplication.java)

`com.tcs.training.notification.NotificationApplication`
[link](./notification-service/src/main/java/com/tcs/training/notification/NotificationApplication.java)

`com.tcs.training.order.AccountApplication`
[link](./order-service/src/main/java/com/tcs/training/order/OrderApplication.java)

`com.tcs.training.payment.PaymentApplication`
[link](./payment-service/src/main/java/com/tcs/training/payment/PaymentApplication.java)


Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Local Application URLs

localhost:8081 - Order
localhost:8082 - Inventory
localhost:8083 - Payment
localhost:8084 - Notification

### Swagger UI

http://localhost:{port}/swagger-ui/index.html
* use above-mentioned ports


## API Test Scripts
Postman API test scripts can be found below.
[link](./postman-tests/E-Commerce-Microservices - Saga Pattern.postman_collection.json)

![img.png](img.png)

Inventory Topic containing created order.
![img_2.png](img_2.png)

Payment topic containing created order.
![img_3.png](img_3.png)

Notification Topic containing created order.
![img_1.png](img_1.png)
![img_4.png](img_4.png)

## Copyright

Released under the Apache License 2.0. See
the [LICENSE](https://github.com/arghyagiri/microservice-e2/blob/main/LICENSE) file.
