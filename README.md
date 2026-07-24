# ORIGEN

> **Enterprise Portfolio Project**
>
> ORIGEN is an enterprise-oriented Full Stack portfolio project designed to demonstrate software architecture, clean code, modular design and modern backend development practices using **Java 21, Spring Boot 3.5, SQL Server and Angular 20**.

---

# Project Status

| Item | Value |
|------|-------|
| Version | 0.2.0 |
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

## Authentication Module

- ✅ RBAC domain model
- ✅ User entity
- ✅ Role entity
- ✅ Permission entity
- ✅ UserRole relationship
- ✅ RolePermission relationship
- ✅ Composite Keys (`@EmbeddedId`)
- ✅ JPA Mapping (`@MapsId`)
- ✅ Flyway migration
- ✅ Initial seed data (Roles & Permissions)

## In Progress

- ⏳ JPA Repositories

## Planned

- JWT Authentication
- Refresh Token
- Login API
- User Management
- Role & Permission Management
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
    │   ├── auth
    │   └── health
    └── security
```

The project follows a **Modular Monolith** architecture where each business module encapsulates its own controllers, services, repositories, entities and DTOs.

Current modules:

```
modules
├── health
└── auth
```

Current Auth structure:

```
auth
├── entity
│   ├── User
│   ├── Role
│   ├── Permission
│   ├── UserRole
│   ├── UserRoleId
│   ├── RolePermission
│   └── RolePermissionId
├── controller
├── dto
├── exception
├── mapper
├── repository
├── service
└── validator
```

---

# Database

Database versioning is managed using **Flyway**.

Current migrations:

| Version | Description |
|----------|-------------|
| V1 | Initial Schema |
| V2 | Authentication (RBAC) Schema |

Current RBAC model:

```
User
    │
UserRole
    │
Role
    │
RolePermission
    │
Permission
```

The application automatically creates the database schema and inserts the initial roles and permissions during startup.

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

Copy:

```
backend/src/main/resources/application-local.example.yml
```

to

```
backend/src/main/resources/application-local.yml
```

Update the values if necessary.

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

During startup, Flyway automatically applies all pending database migrations.

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
- Domain-Driven Design concepts
- Modular Design
- Layered Architecture
- REST API Best Practices
- Incremental Development
- Conventional Commits
- Git Flow
- Database Versioning with Flyway
- Enterprise Software Practices

---

# Roadmap

## Completed

- ✅ Project bootstrap
- ✅ Docker environment
- ✅ SQL Server integration
- ✅ Flyway database versioning
- ✅ Spring Security base configuration
- ✅ OpenAPI / Swagger
- ✅ Health module
- ✅ RBAC domain model
- ✅ Authentication database schema
- ✅ Initial roles and permissions

## Next Milestones

- JPA repositories
- Authentication services
- JWT authentication
- Login endpoint
- Authorization
- User management
- Angular frontend
- CI/CD pipeline

---

# License

MIT License.
