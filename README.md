# ORIGEN

> **Enterprise Portfolio Project**
>
> ORIGEN is an enterprise-oriented Full Stack portfolio project designed to demonstrate software architecture, clean code, modular design and modern backend development practices using **Java 21, Spring Boot 3.5, SQL Server and Angular 20**.

---

# Project Status

| Item | Value |
|------|-------|
| Version | 0.1.0 |
| Status | In Development |
| Architecture | Modular Monolith |
| Java | 21 |
| Spring Boot | 3.5.x |

---

# Project Goal

ORIGEN is not intended to be a tutorial project.

Its purpose is to simulate the architecture, coding standards and development practices commonly used in enterprise software applications.

The project is being built incrementally through small, well-defined commits, allowing the complete development process to be followed from the initial infrastructure to a production-ready application.

---

# Current Progress

## Infrastructure

- ✅ Spring Boot 3.5
- ✅ Java 21
- ✅ Maven
- ✅ Docker
- ✅ Docker Compose
- ✅ SQL Server 2022
- ✅ Flyway
- ✅ Spring Security Base Configuration
- ✅ Swagger / OpenAPI
- ✅ External Configuration
- ✅ Health Module

## In Progress

- ⏳ Authentication Module

## Planned

- JWT Authentication
- Refresh Token
- User Management
- Role & Permission Management (RBAC)
- Angular 20 Frontend
- CI/CD with GitHub Actions

---

# Technologies

## Backend

- Java 21
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- Flyway
- Lombok
- MapStruct
- SpringDoc OpenAPI
- Maven

## Database

- Microsoft SQL Server 2022

## DevOps

- Docker
- Docker Compose

## Frontend (Planned)

- Angular 20
- Angular Material
- SCSS
- TypeScript
- RxJS

---

# Backend Architecture

```
backend
└── src/main/java/cl/origen/platform
    ├── common
    ├── config
    │   └── properties
    ├── modules
    │   └── health
    └── security
```

The project follows a **Modular Monolith** architecture where each business module encapsulates its own controllers, services, repositories, entities and DTOs.

Planned modules:

```
modules
├── health
├── auth
├── users
├── roles
└── ...
```

---

# Repository Structure

```
ORIGEN
├── backend
├── frontend
├── database
├── docker
├── docs
├── .github
├── README.md
└── LICENSE
```

---

# Running the Project

## 1. Start SQL Server

```bash
docker compose -f docker/docker-compose.yml up -d
```

The Docker environment creates a SQL Server instance with the following default configuration:

| Property | Value |
|----------|-------|
| Server | localhost |
| Port | 1433 |
| Database | ORIGEN |
| Username | sa |
| Password | Origen@2026Dev |

---

## 2. Create the local configuration

Copy the example configuration file:

```text
backend/src/main/resources/application-local.example.yml
```

to

```text
backend/src/main/resources/application-local.yml
```

Modify the values if necessary for your local environment.

---

## 3. Build the project

```bash
cd backend
mvn clean install
```

---

## 4. Run the application

```bash
mvn spring-boot:run
```

---

# Available Endpoints

## Swagger UI

```
http://localhost:8080/swagger-ui.html
```

## OpenAPI

```
http://localhost:8080/v3/api-docs
```

## Health Endpoint

```
GET /api/v1/status
```

---

# Development Principles

This project prioritizes:

- Clean Code
- SOLID Principles
- Modular Design
- Layered Architecture
- REST API Best Practices
- Incremental Development
- Conventional Commits
- Git Flow
- Enterprise Software Practices

---

# Roadmap

### Completed

- Project bootstrap
- Docker environment
- SQL Server integration
- Flyway database versioning
- Spring Security base configuration
- OpenAPI / Swagger
- Health module

### Next Milestones

- Authentication module
- JWT authentication
- RBAC (Role-Based Access Control)
- User management
- Angular frontend
- CI/CD Pipeline

---

# License

MIT License.
