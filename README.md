<p align="center">
<img src="docs/images/origen-logo.png" width="220" alt="ORIGEN Logo">
</p>

<p align="center">
<b>Modern Enterprise Full Stack Platform</b>
</p>

<p align="center">
Java 21 • Spring Boot 3.5 • Angular 20 • SQL Server • JWT • Docker
</p>

---

ORIGEN is a modern enterprise Full Stack platform built to demonstrate production-ready software architecture using **Java 21**, **Spring Boot 3.5**, **Angular 20**, **SQL Server**, and **Spring Security**.

The project emphasizes maintainability, scalability, secure authentication, modular software design, and enterprise development practices.

---

# Project Status

| Component | Status |
|-----------|--------|
| Backend | ✅ Stable |
| Authentication | ✅ Completed |
| Authorization (RBAC) | ✅ Completed |
| User Management | ✅ Completed |
| Angular Bootstrap | ✅ Completed |
| Login Module | ✅ Completed |
| Application Layout | ✅ Completed |
| User Management UI | ✅ Completed |
| GitHub Actions CI | ✅ Completed |
| Dashboard | 🚧 In Progress |
| Role Management | 📋 Planned |
| Permission Management | 📋 Planned |

---

# Technology Stack

| Layer | Technologies |
|--------|--------------|
| Backend | Java 21, Spring Boot 3.5 |
| Frontend | Angular 20, Angular Material |
| Security | Spring Security 6, JWT, BCrypt |
| Persistence | Spring Data JPA, Hibernate |
| Database | SQL Server 2022 |
| Database Versioning | Flyway |
| Documentation | OpenAPI / Swagger |
| Infrastructure | Docker, Docker Compose |
| Build | Maven |
| CI | GitHub Actions |

---

# Highlights

- Modern Enterprise Full Stack Platform
- Modular Monolith Architecture
- Java 21 + Spring Boot 3.5
- Angular 20 + Angular Material
- Spring Security 6 + JWT Authentication
- Role-Based Access Control (RBAC)
- Permission-Based Authorization
- Full User Management (CRUD)
- Reactive Forms + Angular Material UI
- Permission-Aware Frontend
- SQL Server + Flyway
- Dockerized Development Environment
- OpenAPI / Swagger
- GitHub Actions CI
- SOLID Principles
- Clean Code

---

# System Overview

<p align="center">
<img src="docs/images/architecture.png" width="100%" alt="ORIGEN Architecture">
</p>

ORIGEN follows a **modular monolith architecture**, where each business module contains its own controllers, services, repositories, DTOs, and entities.

This structure promotes clear separation of responsibilities, maintainability, and scalability while keeping the application simple to develop and deploy.

---

# Application Screenshots

## Angular Login

<p align="center">
<img src="docs/images/login.png" width="100%" alt="Angular Login">
</p>

Modern authentication interface built with Angular 20 and Angular Material.

---

## User Management

<p align="center">
<img src="docs/images/users.png" width="100%" alt="User Management">
</p>

User management interface with CRUD operations, pagination, validation, permission-aware actions, and Angular Material dialogs.

---

## Swagger API

<p align="center">
<img src="docs/images/swagger.png" width="100%" alt="Swagger UI">
</p>

Interactive REST API documentation generated using OpenAPI 3.

---

# Current Features

## Backend

- Java 21
- Spring Boot 3.5
- REST API
- Spring Data JPA
- DTO-based API design
- Bean Validation
- User CRUD
- Duplicate resource validation
- Global Exception Handling
- Standardized API responses

## Frontend

- Angular 20
- Angular Material
- Reactive Forms
- User Management
- Create / Edit / Delete Dialogs
- User Table with Pagination
- Permission-Aware Actions
- Authentication Guards
- JWT HTTP Interceptor
- Success and Error Notifications

## Security

- Spring Security 6
- JWT Authentication
- BCrypt Password Encryption
- Role-Based Access Control (RBAC)
- Permission-Based Authorization
- Method-Level Security
- Stateless Authentication

## Database

- SQL Server 2022
- Spring Data JPA
- Hibernate
- Flyway Database Versioning
- Role and Permission Model

## Infrastructure

- Docker
- Docker Compose
- External Configuration
- Maven Wrapper

## CI

- GitHub Actions
- Maven Build Verification
- Automated Backend Build

---

# Getting Started

## 1. Clone the repository

```bash
git clone https://github.com/esteban-navarro/ORIGEN.git
cd ORIGEN
```

---

## 2. Configure the Backend

Copy:

```text
backend/src/main/resources/application-local.example.yml
```

to:

```text
backend/src/main/resources/application-local.yml
```

and configure your local environment.

---

## 3. Start SQL Server

```bash
docker compose -f docker/docker-compose.yml up -d
```

---

## 4. Run the Backend

```bash
cd backend
./mvnw clean verify
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd clean verify
mvnw.cmd spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

---

## 5. Run the Frontend

Open a new terminal.

```bash
cd frontend
npm install
ng serve
```

Frontend URL:

```text
http://localhost:4200
```

Login using the default administrator account:

| Username | Password |
|----------|----------|
| admin | Admin123* |

---

## 6. API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# Repository Structure

```text
ORIGEN

│
├── backend
├── frontend
├── database
├── docker
├── docs
│   └── images
├── scripts
├── README.md
└── LICENSE
```

The backend follows a modular structure organized by business feature:

```text
backend/src/main/java/cl/origen/platform

├── common
├── config
├── security
└── modules
    ├── auth
    ├── health
    └── user
```

Each module contains the layers required by its responsibilities, such as controllers, services, repositories, DTOs, and entities.

---

# Roadmap

## Completed

- Backend Foundation
- Spring Security
- JWT Authentication
- RBAC Authorization
- User Management
- User Management UI
- SQL Server Integration
- Flyway Migrations
- Docker Environment
- Swagger Documentation
- Angular Bootstrap
- Login Module
- Application Layout
- Frontend Navigation
- Feature Modules
- GitHub Actions CI

---

## In Progress

- Dashboard Module

---

## Planned

- Role Management
- Permission Management
- Refresh Token
- Automated Testing
- Full CI/CD Pipeline

---

# Development Practices

- Modular Monolith
- Modular Architecture
- Layered Architecture
- SOLID Principles
- Clean Code
- REST API Design
- DTO-based API Design
- Conventional Commits
- Git Flow

---

# License

This project is licensed under the MIT License.
