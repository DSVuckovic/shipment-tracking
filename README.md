# Shipment Tracking System

A backend REST API for managing and tracking shipments throughout their lifecycle, built with Spring Boot 4.

## Dependencies

- Java 21
- Spring Boot 4.1.0
- Spring Security + JWT Authentication
- PostgreSQL 16
- Flyway (database migrations)
- MapStruct (object mapping)
- Apache POI (Excel import)
- OpenCSV (CSV import)
- SpringDoc OpenAPI (Swagger UI)
- Docker + Docker Compose

## Features

- JWT-based authentication with role-based access control (USER, ADMIN)
- Shipment management (create, view, update status, delete)
- Full status change history tracking
- Filtering and search by tracking number, description, status, user, and date range
- Bulk import from CSV and Excel files
- One-command Docker Compose setup

## Getting Started

### Prerequisites

- Docker and Docker Compose

### Running with Docker

1. Clone the repository:
```bash
   git clone https://github.com/DSVuckovic/shipment-tracking.git
   cd shipment-tracking
```

2. Create a `.env` file based on the provided template:
```bash
   cp .env.example .env
```

3. Fill in the values in `.env` (see Environment Variables section below)

4. Start the application:
```bash
   docker compose up --build
```

The API will be available at `http://localhost:8080`.  
Swagger UI is available at `http://localhost:8080/swagger-ui.html` for testing endpoints.

### Running Locally (without Docker)

#### Prerequisites
- Java 21
- Maven
- PostgreSQL

1. Create a PostgreSQL database named `shipment_tracking`
2. Set the environment variables listed below
3. Run the application:
```bash
   mvn spring-boot:run
```

## Environment Variables

| Variable            | Description                                   | Default                                              |
|---------------------|-----------------------------------------------|------------------------------------------------------|
| `DATABASE_URL`      | PostgreSQL JDBC URL                           | `jdbc:postgresql://localhost:5432/shipment_tracking` |
| `DATABASE_USERNAME` | PostgreSQL username                           | `postgres`                                           |
| `DATABASE_PASSWORD` | PostgreSQL password                           | `postgres`                                           |
| `JWT_SECRET`        | Secret key for JWT signing (minimum 64 chars) | —                                                    |
| `JWT_EXPIRATION`    | JWT expiration time in milliseconds           | `86400000` (24h)                                     |
| `SERVER_PORT`       | Application port                              | `8080`                                               |

## API Overview

### Authentication
| Method | Endpoint         | Description                 | Access |
|--------|------------------|-----------------------------|--------|
| POST   | `/auth/register` | Register a new user         | Public |
| POST   | `/auth/login`    | Login and receive JWT token | Public |

### Shipments
| Method | Endpoint                  | Description                   | Access                  |
|--------|---------------------------|-------------------------------|-------------------------|
| POST   | `/shipments`              | Create a new shipment         | USER, ADMIN             |
| GET    | `/shipments`              | Get all shipments             | USER (own), ADMIN (all) |
| GET    | `/shipments/{id}`         | Get shipment by ID            | USER (own), ADMIN       |
| PATCH  | `/shipments/{id}/status`  | Update shipment status        | USER (own), ADMIN       |
| DELETE | `/shipments/{id}`         | Delete a shipment             | ADMIN                   |
| GET    | `/shipments/{id}/history` | Get status change history     | USER (own), ADMIN       |
| GET    | `/shipments/search`       | Search and filter shipments   | USER, ADMIN             |
| POST   | `/shipments/import`       | Bulk import from CSV or Excel | ADMIN                   |

## Shipment Status Flow
```text
CREATED → IN_TRANSIT → DELIVERED
        ↘     ↓
          CANCELLED
```


## Bulk Import

The `/shipments/import` endpoint accepts `.csv` or `.xlsx` files.

Expected file format:

| Tracking Number | Description         | Status  | Created At          |
|-----------------|---------------------|---------|---------------------|
| TRK-100001      | Electronics package | CREATED | 2026-01-01 10:00:00 |

- **Tracking Number** must be unique
- **Status** must be one of: `CREATED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`
- **Created At** format: `yyyy-MM-dd HH:mm:ss` or ISO (optional, defaults to current time if omitted)

Shipments imported as such will have their **Created By** field set to the user id importing them.

## Default Seed Data

The application is preloaded with testing data (using Flyway):

### Users
| Id | Username | First Name | Last Name    | Email                 | Role  | Created At                                           | Password |
|----|----------|------------|--------------|-----------------------|-------|------------------------------------------------------|----------|
| 1  | `user1`  | `John`     | `Smith`      | `johnsmith@gmail.com` | USER  | The date and time you run the app for the first time | `user`   |
| 2  | `user2`  | `Jane`     | `Smith`      | `janesmith@gmail.com` | USER  |                                                      | `user`   |
| 3  | `user3`  | `Jack`     | `Smith`      | `jacksmith@gmail.com` | USER  |                                                      | `user`   |
| 4  | `admin`  | `Admin`    | `Privileges` | `admin@gmail.com`     | ADMIN |                                                      | `admin`  |


### Shipments

| Id | Tracking Number | Created By | Description           | Created At                                           | Status       |
|----|-----------------|------------|-----------------------|------------------------------------------------------|--------------|
| 1  | `TRK-100001`    | `user1`    | `Electronics package` | The date and time you run the app for the first time | `DELIVERED`  | 
| 2  | `TRK-100002`    | `user2`    | `Books shipment`      |                                                      | `IN_TRANSIT` |
| 3  | `TRK-100003`    | `user3`    | `Furniture delivery`  |                                                      | `CANCELLED`  |
| 4  | `TRK-100004`    | `user1`    | `Clothing package`    |                                                      | `CREATED`    |

### Status Changes

| Change Id | Shipment ID | Changed By | Old Status   | New Status   | Changed At                                           | Description             |
|-----------|-------------|------------|--------------|--------------|------------------------------------------------------|-------------------------|
| 1         | 1           | `user1`    | `CREATED`    | `IN_TRANSIT` | The date and time you run the app for the first time | `'Shipment picked up '` |
| 2         | 1           | `user1`    | `IN_TRANSIT` | `DELIVERED`  |                                                      | `'Shipment delivered'`  |
| 3         | 2           | `user2`    | `CREATED`    | `IN_TRANSIT` |                                                      | `'Shipment picked up '` |
| 4         | 3           | `user3`    | `CREATED`    | `CANCELLED`  |                                                      | `'Shipment cancelled '` |

## Project Structure

src/main/java/com/damjan_vuckovic/shipment_tracking/

├── config/ # Security, constants and global exception handler

├── controller/ # REST controllers

├── dto/ # Data transfer objects

├── enums/ # Enums (Role, ShipmentStatus)

├── exception/ # Custom Exception types

├── mapper/ # MapStruct mappers

├── model/ # JPA entities

├── repository/ # Spring Data repositories

├── security/ # JWT filter, CustomUserDetails

├── service/ # Business logic

│   └── parser/ # CSV and Excel import strategies

├── specification/ # JPA Specification mapper for search function

└── utils/ # utilities

## Authors

* Damjan Vučković


