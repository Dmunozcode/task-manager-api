# Task Manager API

Task Manager API is a basic REST API built with Java and Spring Boot.

It allows users to create, read, update, complete, and delete tasks using HTTP endpoints. This project is part of my backend learning path and focuses on understanding the fundamentals of REST APIs, controllers, request bodies, path variables, and basic in-memory data handling.

## Tech Stack

- Java 21
- Spring Boot
- Maven
- Postman

## Features

- Health check endpoint
- Create tasks
- List all tasks
- Find a task by ID
- Update a task
- Mark a task as completed
- Delete a task

## Project Structure

```text
src/main/java
└── com.daniel.task.manager.api
    ├── controller
    │   ├── HealthController.java
    │   └── TaskController.java
    ├── model
    │   └── Task.java
    └── TaskManagerApiApplication.java
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

## Example Requests

### Create a Task

```http
POST /tasks
Content-Type: application/json
```

```json
{
  "title": "Learn Spring Boot",
  "description": "Create GET and POST endpoints"
}
```

### Update a Task

```http
PUT /tasks/1
Content-Type: application/json
```

```json
{
  "title": "Learn REST APIs",
  "description": "Update an existing task",
  "completed": true
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

The API will be available at:

```text
http://localhost:8080
```

## Current Limitations

- Data is stored in memory, so tasks are lost when the application restarts.
- Error handling is still basic.
- There is no database integration yet.

## Next Improvements

- Add proper error handling with `404 Not Found`
- Add input validation
- Move business logic to a service layer
- Add unit tests
- Add database persistence with JPA

## Author

Daniel

Backend learning project built with Java and Spring Boot.