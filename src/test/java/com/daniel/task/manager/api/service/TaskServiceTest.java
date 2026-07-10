package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task(null, "Learn JUnit", "Write my first unit test");

        Task createdTask = taskService.createTask(task);

        assertEquals(1L, createdTask.getId());
        assertEquals("Learn JUnit", createdTask.getTitle());
        assertEquals("Write my first unit test", createdTask.getDescription());
        assertFalse(createdTask.isCompleted());
    }

    @Test
    void shouldReturnAllTasks() {
        Task task = new Task(null, "Learn JUnit", "Test getAllTasks");

        taskService.createTask(task);

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Learn JUnit", tasks.get(0).getTitle());
        assertEquals("Test getAllTasks", tasks.get(0).getDescription());
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTasks() {
        List<Task> tasks = taskService.getAllTasks();

        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnTaskById() {
        Task task = new Task(null, "Learn JUnit", "Test getTaskById");

        Task createdTask = taskService.createTask(task);

        Task foundTask = taskService.getTaskById(createdTask.getId());

        assertEquals(1L, foundTask.getId());
        assertEquals("Learn JUnit", foundTask.getTitle());
        assertEquals("Test getTaskById", foundTask.getDescription());
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task(null, "Learn JUnit", "Test updateTask");

        Task createdTask = taskService.createTask(task);

        Task updatedData = new Task(null, "New Task", "Test updateTask");

        Task updatedTask = taskService.updateTask(createdTask.getId(), updatedData);

        assertEquals(createdTask.getId(), updatedTask.getId());
        assertEquals("New Task", updatedTask.getTitle());
        assertEquals("Test updateTask", updatedTask.getDescription());
    }

    @Test
    void shouldCompleteTask() {
        Task task = new Task(null, "Learn JUnit", "Test completeTask");

        Task createdTask = taskService.createTask(task);

        Task completedTask = taskService.completeTask(createdTask.getId());

        assertTrue(completedTask.isCompleted());
        assertEquals(createdTask.getId(), completedTask.getId());
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task(null, "Learn JUnit", "Test deleteTask");

        Task createdTask = taskService.createTask(task);

        taskService.deleteTaskById(createdTask.getId());

        assertThrows(ResponseStatusException.class, () -> {
            taskService.getTaskById(createdTask.getId());
        });
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
           taskService.getTaskById(999L);
        });

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingMissingTask() {
        Task updatedData = new Task(null, "New Task", "Test Exception");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
           taskService.updateTask(999L, updatedData);
        });

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldThrowExceptionWhenCompletingMissingTask() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            taskService.completeTask(999L);
        });

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingTask() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            taskService.deleteTaskById(999L);
        });

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        Task task = new Task(null, "", "Test Exception");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
           taskService.createTask(task);
        });

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsBlank() {
        Task task = new Task(null, "Test exception", "");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
           taskService.createTask(task);
        });

        assertEquals(400, exception.getStatusCode().value());
    }
}
