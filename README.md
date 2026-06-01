# Library API

A layered Spring Boot REST API for managing a library system (books, authors, genres, members, and loans) with domain-driven modelling, strict business rules, and MySQL persistence via Flyway migrations.

---

## Tech Stack

- Spring Boot 3.5.1
- Java 21
- MySQL 8.0
- Spring Data JPA / Hibernate
- Flyway
- Jakarta Bean Validation
- Maven
- Docker / Docker Compose

---

## Getting Started

### Prerequisites
- Docker + Docker Compose (recommended)

### Run with Docker

docker compose up --build

Services:
- MySQL on 3306
- API on 8080

Flyway runs automatically on startup.

Default DB credentials: root / root (local only)

---

## API Overview

All responses:

{
"message": "...",
"path": "...",
"data": {}
}

Errors use "errors" instead of "data".

---

## Core Endpoints

Authors / Genres (CRUD)
- GET /authors, POST /authors, DELETE /authors/{id}
- GET /genres, POST /genres, DELETE /genres/{id}

---

Books
- GET /books
- POST /books
- GET /books/{bookId}/authors
- POST /books/{bookId}/authors
- DELETE /books/{bookId}/authors/{authorId}
- POST /books/{bookId}/genres
- DELETE /books/{bookId}/genres/{genreId}

Example:

{
"title": "A Game of Thrones",
"isbn": "9780553103540",
"publishedYear": 1996
}

---

Book Copies
- GET /book-copies/{id}
- GET /book-copies/code/{copyCode}
- GET /books/{bookId}/copies?inCirculation=true
- POST /books/{bookId}/copies
- PATCH /book-copies/{copyCode}

Example:

{ "inCirculation": false }

---

Members
- GET /members
- POST /members
- PATCH /members/{memberCode}/activate
- PATCH /members/{memberCode}/deactivate

Example:

{ "first": "Jane", "last": "Doe", "email": "jane@example.com" }

---

Loans
- GET /loans?status=ACTIVE|INACTIVE
- GET /loans/{id}
- POST /loans
- POST /loans/return

Example:

{ "memberCode": "MEM-AB12CD34", "copyCode": "BC-XY9876543Z" }

---

## Architecture Highlights

- Explicit join entities instead of ManyToMany
- DTOs fully decouple API from persistence layer
- Global exception handler ensures consistent responses
- Loan rules enforced in service layer
- Flyway manages versioned schema migrations
- Clean separation: controller → service → repository

---

## Error Handling

ResourceNotFoundException -> entity not found  
ResourceInUseException -> cannot delete referenced entity  
DuplicateIsbnException -> book already exists  
EmailAlreadyInUseException -> duplicate member email  
MemberInactiveException -> inactive member loan attempt  
BookAlreadyLoanedException -> copy already on loan  
NoActiveLoanException -> return without active loan  
CopyHasLoanHistoryException -> cannot delete copy  
AuthorAlreadyLinkedException -> duplicate author link  
GenreAlreadyLinkedException -> duplicate genre link

---

## Project Structure

controller/   REST layer  
service/      business logic  
repository/   data access  
model/        entities  
dto/          API contracts  
exception/    global error handling  
specification query filters  
util/         ID/code generation

---

## Summary

This project demonstrates:

- Layered Spring Boot architecture
- Domain-driven library system modelling
- DTO-based API separation
- Global exception handling strategy
- Dockerised development setup
- Flyway-managed database migrations