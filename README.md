# Task Manager API

Task Manager API is a REST API built with Java and Spring Boot to manage tasks.

The project allows users to create, list, search, update, complete, and delete tasks through HTTP endpoints. It also includes basic validation, error handling, a service layer, and unit tests with JUnit.

## Tech Stack

- Java 21
- Spring Boot
- Maven
- Springdoc OpenAPI
- Swagger UI
- JUnit 5
- Postman

## Features

- Health check endpoint
- Create tasks
- List all tasks
- Find a task by ID
- Update task data
- Mark a task as completed
- Delete tasks
- Basic input validation
- Basic error handling with proper HTTP status codes
- Unit tests for the service layer
- Interactive API documentation with Swagger UI
- OpenAPI specification generated automatically

## Project Structure

```text
src
├── main
│   └── java
│       └── com.daniel.task.manager.api
│           ├── controller
│           │   ├── HealthController.java
│           │   └── TaskController.java
│           ├── model
│           │   └── Task.java
│           ├── service
│           │   └── TaskService.java
│           └── TaskManagerApiApplication.java
└── test
    └── java
        └── com.daniel.task.manager.api
            ├── service
            │   └── TaskServiceTest.java
            └── TaskManagerApiApplicationTests.java
```

## Task Model

A task contains the following fields:

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Create a basic REST API",
  "completed": false
}
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/health` | Checks if the API is running |
| GET | `/tasks` | Returns all tasks |
| POST | `/tasks` | Creates a new task |
| GET | `/tasks/{id}` | Returns a task by ID |
| PUT | `/tasks/{id}` | Updates a task by ID |
| PATCH | `/tasks/{id}/complete` | Marks a task as completed |
| DELETE | `/tasks/{id}` | Deletes a task by ID |

## API Documentation

The API includes interactive documentation generated with Springdoc OpenAPI.

Swagger UI allows developers to explore the available endpoints, inspect request and response schemas, and execute HTTP requests directly from the browser.

With the application running, the documentation is available at:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Example Requests

### Health Check

```http
GET /health
```

Example response:

```text
Task Manager API is running
```

### Create a Task

```http
POST /tasks
Content-Type: application/json
```

Request body:

```json
{
  "title": "Learn Spring Boot",
  "description": "Create GET and POST endpoints"
}
```

Example response:

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Create GET and POST endpoints",
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
    "title": "Learn Spring Boot",
    "description": "Create GET and POST endpoints",
    "completed": false
  }
]
```

If there are no tasks, the API returns an empty list:

```json
[]
```

### Get Task By ID

```http
GET /tasks/1
```

Example response:

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Create GET and POST endpoints",
  "completed": false
}
```

### Update a Task

```http
PUT /tasks/1
Content-Type: application/json
```

Request body:

```json
{
  "title": "Learn REST APIs",
  "description": "Update an existing task",
  "completed": false
}
```

Example response:

```json
{
  "id": 1,
  "title": "Learn REST APIs",
  "description": "Update an existing task",
  "completed": false
}
```

### Complete a Task

```http
PATCH /tasks/1/complete
```

Example response:

```json
{
  "id": 1,
  "title": "Learn REST APIs",
  "description": "Update an existing task",
  "completed": true
}
```

### Delete a Task

```http
DELETE /tasks/1
```

## Validation

The API validates task data when creating or updating tasks.

A task must have:

- A non-empty title
- A non-empty description

Invalid request example:

```json
{
  "title": "",
  "description": "Invalid task example"
}
```

Response:

```text
400 Bad Request
```

## Error Handling

The API returns proper HTTP status codes for basic error cases.

| Status Code | Meaning | Example |
|------------|---------|---------|
| 400 Bad Request | Invalid task data | Empty title or description |
| 404 Not Found | Task does not exist | Searching for `/tasks/999` |

Example:

```http
GET /tasks/999
```

Response:

```text
404 Not Found
```

## Testing

Unit tests are implemented with JUnit for the service layer.

The tests cover:

- Creating tasks
- Returning all tasks
- Returning an empty task list
- Finding a task by ID
- Updating a task
- Completing a task
- Deleting a task
- Handling missing tasks
- Validating empty title and description

Run tests with Maven Wrapper:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

Or in PowerShell:

```powershell
.\mvnw.cmd test
```

## How to Run

Clone the repository:

```bash
git clone https://github.com/Dmunozcode/task-manager-api.git
```

Go into the project folder:

```bash
cd task-manager-api
```

Run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

Or in PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## Current Limitations

- Data is stored in memory, so tasks are lost when the application restarts.
- There is no database integration yet.
- Error responses use Spring Boot's default error format.

## Next Improvements

- Data persistence using Spring Data JPA.
- PostgreSQL database integration.
- Repository layer using JpaRepository.
- Improved validation using Bean Validation.
- Global error handling for clearer API responses.
- Additional tests for the service and controller layers.

## Author

Daniel

Backend learning project built with Java and Spring Boot.