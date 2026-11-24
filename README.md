# Cinema Lokah Backend

A minimal Spring Boot REST API starter using Java 17 and Maven.

## Requirements

- JDK 17 or newer
- Maven 3.6 or newer

## Run

```sh
mvn spring-boot:run
```

The server listens on port `8080` by default. Set `PORT` to choose a different port.

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

Start adding application features under `com.cinemalokah.backend`, with corresponding tests under `src/test/java`.
