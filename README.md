# Cinema Lokah Backend

A Spring Boot REST API for Cinema Lokah, packaged as a Docker web service for Render and using Java 17.

## Requirements

- JDK 17 or newer
- Maven 3.6 or newer

## Run

```sh
mvn spring-boot:run
```

The server binds to `0.0.0.0` and listens on `PORT` (default `10000`, matching Render). Application settings are in `src/main/resources/application.properties`.

## Endpoints

- `GET /api/health` — application health and service name
- `GET /actuator/health` — Spring Boot Actuator health
- `GET /actuator/health/liveness` and `/actuator/health/readiness` — deployment probes

## Test and package

```sh
mvn test
mvn package
java -jar target/cinema-lokah-backend-0.0.1-SNAPSHOT.jar
```

## Deploy to Render

This repository includes a Render Blueprint (`render.yaml`) and a multi-stage Dockerfile. Connect the repository in Render and create the service from its Blueprint, or create a Docker Web Service with the repository root as the Dockerfile context. Set `TMDB_ACCESS_TOKEN` in the Render service environment before the app starts; the Blueprint prompts for this secret. Keep the token out of source control. Render provides `PORT` automatically.

Render health checks use `GET /api/health`. Actuator liveness and readiness endpoints are also available under `/actuator/health/`.

For local Docker use:

```sh
docker build -t cinema-lokah-backend .
docker run --rm -p 10000:10000 -e TMDB_ACCESS_TOKEN=your-token cinema-lokah-backend
```

Start adding application features under `com.cinemalokah.backend`, with corresponding tests under `src/test/java`.
