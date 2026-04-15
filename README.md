# Product Pricing Service

Spring Boot REST service that returns the applicable product price for a given application date, product, and brand.

## Hexagonal + DDD Structure

The project is organized following the Baeldung style of three layers:

- `domain` (inside): business model, policies, and domain ports
- `application` (outside): REST API controllers and request/response handling
- `infrastructure` (outside): Spring configuration and persistence adapters

Current package layout:

- `com.example.productpricingservice.domain.model`
- `com.example.productpricingservice.domain.port`
- `com.example.productpricingservice.domain.service`
- `com.example.productpricingservice.domain.exception`
- `com.example.productpricingservice.application.rest`
- `com.example.productpricingservice.application.rest.dto`
- `com.example.productpricingservice.infrastructure.adapter.persistence`
- `com.example.productpricingservice.infrastructure.adapter.persistence.entity`
- `com.example.productpricingservice.infrastructure.adapter.persistence.repository`
- `com.example.productpricingservice.infrastructure.config`

## Request Flow Diagram

This diagram shows how a GET /api/prices request moves through the hexagonal layers, how the applicable price is selected, and how the response returns to the client.

![Request flow](docs/request-flow.svg)

## Project Starup Guide

- [Start Project Guide](docs/START-PROJECT.md)


## API

Endpoint:

- `GET /api/prices`

Query parameters:

- `applicationDate` (ISO date-time, e.g. `2020-06-14T16:00:00`)
- `productId` (e.g. `35455`)
- `brandId` (e.g. `1`)

Response:

- `productId`
- `brandId`
- `priceList`
- `startDate`
- `endDate`
- `price`
- `currency`

Example request:

```bash
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```

## Local Run

Prerequisites:

- Java 21+
- Gradle wrapper (`gradlew` / `gradlew.bat`)

Build and test:

```bash
./gradlew clean test
```

Windows:

```powershell
.\gradlew.bat clean test
```

Run application:

```bash
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

## H2 Initialization

The in-memory H2 database is initialized at startup using:

- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`


## Useful URLs

- App: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
