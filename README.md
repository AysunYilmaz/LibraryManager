
# Microservice  - Library Manager

## Introduction

This is a Library Manager Microservice . It consists of two services (book service, lend service) .
The API implemented using Spring Boot Framework
Api documentation can be accessible using swagger : http://localhost:8082/v2/api-docs?group=Api


Implemented use cases:

* Add a book
* List books
* Borrow a book
* Return a book
* List borrowed books

## How to run

mvn spring-boot:run

## Microservice has following endpoints

    GET /api/v1.0/books "Get All the books in the library"

    GET /api/v1.0/books/{id}    "Get book by id"
    
    POST /api/v1.0/books  "Adds book into library"
    
    DELETE /api/v1.0/books  "Removes book from library"

    GET /api/v1.0/lend  "Get all borrowed books"
    
    GET /api/v1.0/lend/{lendId} "Get borrowed book by lendId"
    
    GET /api/v1.0/lend/customer/{customerName}  "Get borrowed books by Customer"
    
    POST /api/v1.0/lend  "Borrow"
    
    PUT /api/v1.0/lend/extend/{lendId}  "Update expiry date of the borrowed book"
    
    PUT /api/v1.0/lend/return/{lendId}  "Return borrowed book"
