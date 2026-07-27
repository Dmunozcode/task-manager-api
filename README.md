# Task Manager API

Task Manager API is a RESTful API built with Java and Spring Boot for managing tasks.

The project follows a layered architecture and includes persistent storage with PostgreSQL, DTO-based request and response models, Bean Validation, global exception handling, interactive OpenAPI documentation, and unit tests with JUnit and Mockito.

## Features

- Create tasks
- List all tasks
- Find a task by ID
- Update task data
- Mark a task as completed
- Delete tasks
- Persist tasks in PostgreSQL
- Validate incoming requests
- Return structured JSON error responses
- Interactive API documentation with Swagger UI
- Unit tests for the service layer
- PostgreSQL environment managed with Docker Compose

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Application framework |
| Spring Web MVC | REST API and HTTP endpoints |
| Spring Data JPA | Repository and persistence abstraction |
| Hibernate | JPA implementation and object-relational mapping |
| PostgreSQL 18 | Relational database |
| Bean Validation | Request validation |
| Springdoc OpenAPI | OpenAPI specification generation |
| Swagger UI | Interactive API documentation |
| JUnit 5 | Unit testing |
| Mockito | Mocking repository dependencies in unit tests |
| Maven | Build and dependency management |
| Docker Compose | PostgreSQL container orchestration |

## Architecture

The application follows a layered structure:

```text
HTTP Request
     |
     v
Controller
     |
     v
Service
     |
     v
Repository
     |
     v
PostgreSQL
```

DTOs are used to separate the public API contract from the persistence entity:

- `TaskRequest` represents incoming task data.
- `TaskResponse` represents data returned by the API.
- `Task` is the JPA entity persisted in PostgreSQL.

## Project Structure

```text
task-manager-api/
|-- src/
|   |-- main/
|   |   |-- java/com/daniel/task/manager/api/
|   |   |   |-- controller/
|   |   |   |   |-- HealthController.java
|   |   |   |   `-- TaskController.java
|   |   |   |-- dto/
|   |   |   |   |-- TaskRequest.java
|   |   |   |   `-- TaskResponse.java
|   |   |   |-- exception/
|   |   |   |   |-- ErrorResponse.java
|   |   |   |   |-- GlobalExceptionHandler.java
|   |   |   |   `-- TaskNotFoundException.java
|   |   |   |-- model/
|   |   |   |   `-- Task.java
|   |   |   |-- repository/
|   |   |   |   `-- TaskRepository.java
|   |   |   |-- service/
|   |   |   |   `-- TaskService.java
|   |   |   `-- TaskManagerApiApplication.java
|   |   `-- resources/
|   |       `-- application.properties
|   `-- test/
|       `-- java/com/daniel/task/manager/api/service/
|           `-- TaskServiceTest.java
|-- .env.example
|-- .gitignore
|-- docker-compose.yml
|-- mvnw
|-- mvnw.cmd
|-- pom.xml
`-- README.md
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/health` | Checks whether the API is running |
| GET | `/tasks` | Returns all tasks |
| POST | `/tasks` | Creates a task |
| GET | `/tasks/{id}` | Returns a task by ID |
| PUT | `/tasks/{id}` | Updates a task |
| PATCH | `/tasks/{id}/complete` | Marks a task as completed |
| DELETE | `/tasks/{id}` | Deletes a task |

## Task Representation

A task returned by the API has the following structure:

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Build a task management REST API",
  "completed": false
}
```

## Example Requests

### Create a Task

```http
POST /tasks
Content-Type: application/json
```

```json
{
  "title": "Learn Docker Compose",
  "description": "Run PostgreSQL inside a Docker container"
}
```

Example response:

```json
{
  "id": 1,
  "title": "Learn Docker Compose",
  "description": "Run PostgreSQL inside a Docker container",
  "completed": false
}
```

### Get All Tasks

```http
GET /tasks
```

Example response:

```json
[
  {
    "id": 1,
    "title": "Learn Docker Compose",
    "description": "Run PostgreSQL inside a Docker container",
    "completed": false
  }
]
```

When no tasks exist, the API returns an empty JSON array:

```json
[]
```

### Get a Task by ID

```http
GET /tasks/1
```

### Update a Task

```http
PUT /tasks/1
Content-Type: application/json
```

```json
{
  "title": "Learn Docker",
  "description": "Understand containers, images, and volumes"
}
```

### Complete a Task

```http
PATCH /tasks/1/complete
```

### Delete a Task

```http
DELETE /tasks/1
```

## Validation

Incoming task requests are validated using Bean Validation.

Validation rules:

- `title` is required and must not exceed 100 characters.
- `description` is required and must not exceed 500 characters.

Invalid request:

```json
{
  "title": "",
  "description": ""
}
```

Example validation response:

```json
{
  "timestamp": "2026-07-27T12:00:00",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "title": "Title is required",
    "description": "Description is required"
  }
}
```

## Error Handling

A global exception handler produces consistent JSON error responses.

| Status | Situation |
|---|---|
| `400 Bad Request` | Request validation fails |
| `404 Not Found` | The requested task does not exist |

Example not-found response:

```json
{
  "timestamp": "2026-07-27T12:00:00",
  "status": 404,
  "message": "Task not found with id: 999",
  "errors": {}
}
```

## API Documentation

Springdoc OpenAPI automatically generates the API specification and Swagger UI.

With the application running:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Swagger UI can be used to inspect schemas and execute requests directly from the browser.

## Running the Project

### Prerequisites

- Java 21
- Docker Desktop
- Git

PostgreSQL does not need to be installed locally because it runs in a Docker container.

### 1. Clone the Repository

```bash
git clone https://github.com/Dmunozcode/task-manager-api.git
cd task-manager-api
```

### 2. Configure the Database Password

Create a `.env` file in the project root based on `.env.example`:

```env
DB_PASSWORD=your_development_password
```

The `.env` file is ignored by Git and must not be committed.

The Spring Boot application also requires `DB_PASSWORD` as an environment variable. Its value must match the password configured in `.env`.

In PowerShell:

```powershell
$env:DB_PASSWORD="your_development_password"
```

In Bash:

```bash
export DB_PASSWORD="your_development_password"
```

When running the application from IntelliJ IDEA, add `DB_PASSWORD` to the environment variables of the Spring Boot run configuration.

### 3. Start PostgreSQL

```bash
docker compose up -d
```

Check the container status:

```bash
docker compose ps
```

PostgreSQL is exposed on:

```text
localhost:5433
```

Docker creates the `task_manager` database automatically and stores its data in the `postgres_data` volume.

### 4. Run the Application

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

### 5. Stop PostgreSQL

```bash
docker compose down
```

This removes the container and Docker network but preserves the database volume.

To remove the container and all stored database data:

```bash
docker compose down -v
```

## Testing

Service-layer unit tests are implemented with JUnit 5 and Mockito.

Mockito isolates the service from PostgreSQL by replacing `TaskRepository` with a mock dependency.

The tests cover:

- Creating a task
- Returning all tasks
- Returning an empty task list
- Finding a task by ID
- Updating a task
- Completing a task
- Deleting a task
- Handling a missing task

Run the tests with:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Future Improvements

- Add controller and integration tests
- Use Testcontainers for PostgreSQL integration tests
- Dockerize the Spring Boot application
- Add pagination, filtering, and sorting
- Add task deadlines and priorities
- Add authentication and authorization
- Add a continuous integration pipeline

## Author

Daniel Muñoz

Backend development learning project built with Java and Spring Boot.