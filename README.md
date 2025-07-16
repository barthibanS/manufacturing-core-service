# Manufacturing Core Service

## Overview
Manufacturing Core Service is a Spring Boot application designed to manage manufacturing operations, including device management, group assignments, plant configurations, and performance tracking. It provides RESTful APIs for core manufacturing data and integrates with modern DevOps workflows.

## Features
- Device and plant management
- Group and assignment handling
- Performance tracking and reporting
- Secure authentication and session management
- RESTful API endpoints
- Dockerized deployment
- Automated CI/CD with GitHub Actions

## API Endpoints

### Device APIs (`/api/devices`)
- `GET /api/devices` — List all devices
- `GET /api/devices/{id}` — Get device by ID
- `POST /api/devices` — Create a new device
- `PUT /api/devices/{id}` — Update device by ID
- `DELETE /api/devices/{id}` — Delete device by ID
- `GET /api/devices/{deviceId}/events` — Stream device events (Server-Sent Events)

### Group APIs (`/api/groups`)
- `GET /api/groups` — List all groups
- `GET /api/groups/{id}` — Get group by ID
- `POST /api/groups` — Create a new group
- `PUT /api/groups/{id}` — Update group by ID
- `DELETE /api/groups/{id}` — Delete group by ID
- `GET /api/groups/{groupId}/performance` — Get group performance stats

### Person APIs (`/api/persons`)
- `GET /api/persons` — List all persons
- `GET /api/persons/{id}` — Get person by ID
- `POST /api/persons` — Create a new person
- `PUT /api/persons/{id}` — Update person by ID
- `DELETE /api/persons/{id}` — Delete person by ID

### Device Login APIs (`/api`)
- `POST /api/devices/{deviceId}/login?personId={personId}` — Log in a person to a device
- `POST /api/devices/{deviceId}/logout?personId={personId}` — Log out a person from a device
- `GET /api/employees/{personId}/timelogs` — Get time logs for a person

### Assignment APIs (`/assignments`)
- `POST /assignments/employee-to-group?personId={personId}&groupId={groupId}` — Assign employee to group
- `POST /assignments/group-to-plant?groupId={groupId}&plantId={plantId}` — Assign group to plant
- `POST /assignments/device-to-plant?deviceId={deviceId}&plantId={plantId}` — Assign device to plant
- `POST /assignments/group-to-device?groupId={groupId}&deviceId={deviceId}` — Assign group to device

### Plant APIs (`/plants`)
- `GET /plants` — List all plants

## Prerequisites
- Java 17+
- Maven 3.6+
- Docker (for containerization)
- GitHub account (for CI/CD)

## Project Structure
```
manufacturing-core-service/
├── manufacturing-core-service/         # Main Spring Boot application
│   ├── src/                           # Source code
│   ├── pom.xml                        # Maven build file
│   └── Dockerfile                     # Docker build file
├── .github/
│   └── workflows/
│       └── ci-cd.yml                  # GitHub Actions workflow
└── README.md
```

## Technical Stack & Techniques

- **Framework:** Spring Boot (RESTful API, Dependency Injection, Configuration)
- **ORM:** Spring Data JPA (with Hibernate, using repository pattern and JPA derived queries)
- **Database:** Relational (configured via `application.properties`, schema in `schema.sql`)
- **Entities/DTOs:** Java classes with JPA annotations, Lombok for boilerplate reduction
- **API Design:** RESTful, with standard CRUD and custom endpoints
- **Reactive Programming:** Server-Sent Events (SSE) for device event streaming using Spring WebFlux (`Flux`, `WebClient`)
- **Security:** Spring Security (with a permissive config, can be extended for authentication/authorization)
- **Exception Handling:** Global exception handler using `@ControllerAdvice`
- **Testing:** JUnit (see `src/test/java`)
- **DevOps:** Docker for containerization, GitHub Actions for CI/CD
- **Logging:** SLF4J with Logback (default in Spring Boot)
- **Configuration:** Externalized via `application.properties` and YAML for Helm charts (Kubernetes deployment ready)
- **Best Practices:** Layered architecture (Controller, Service, Repository), DTO usage, validation, and error handling

## Build & Run (Locally)
1. **Clone the repository:**
   ```sh
   git clone <repo-url>
   cd manufacturing-core-service
   ```
2. **Build the project:**
   ```sh
   ./manufacturing-core-service/mvnw clean install
   ```
3. **Run the application:**
   ```sh
   java -jar manufacturing-core-service/target/*.jar
   ```

## Running Tests
```sh
./manufacturing-core-service/mvnw test
```

## Docker Usage
1. **Build Docker image:**
   ```sh
   docker build -t my-app:latest ./manufacturing-core-service
   ```
2. **Run Docker container:**
   ```sh
   docker run -p 8080:8080 my-app:latest
   ```

## CI/CD Pipeline
- **Location:** `.github/workflows/ci-cd.yml`
- **What it does:**
  - Triggers on push and pull request to `main` branch
  - Builds and tests the project using Maven
  - Builds a Docker image
  - (Optional) Can be extended to push Docker images to a registry

## Configuration
- Application properties: `manufacturing-core-service/src/main/resources/application.properties`
- Database schema/data: `manufacturing-core-service/src/main/resources/schema.sql` and `data.sql`

## Customization
- Update Docker image name/tag as needed in the workflow and Docker commands.
- Add secrets to GitHub for Docker registry pushes if required.

## License
[Specify your license here]

## Contact
For questions or support, contact [your-email@example.com].
