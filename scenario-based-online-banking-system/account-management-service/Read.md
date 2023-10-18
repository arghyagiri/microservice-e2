# Account Management Service

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[Spring Boot](http://projects.spring.io/spring-boot/) based app.

## Responsibilities: 
* Handle CRUD operations for the user account, manage balance.

## APIs:
* ```/createAccount```: Create a new user account.
* ```/debit```: Debit the user's account.
* ```/credit```: Credit the user's account.
## Event Listeners:
* TransactionFailedEvent: Listen for failed transactions and compensate by crediting the account.
## Copyright

Released under the Apache License 2.0. See
the [LICENSE](https://github.com/arghyagiri/microservice-e2/blob/main/LICENSE) file.
