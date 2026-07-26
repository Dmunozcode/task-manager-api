package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.dto.TaskRequest;
import com.daniel.task.manager.api.dto.TaskResponse;
import com.daniel.task.manager.api.exception.TaskNotFoundException;
import com.daniel.task.manager.api.model.Task;
import com.daniel.task.manager.api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTask() {
        TaskRequest request = new TaskRequest("Learn Mockito", "Write my first Mockito test");

        Task savedTask = new Task("Learn Mockito", "Write my first Mockito test");
        savedTask.setId(1L);

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request);

        assertEquals(1L, response.getId());
        assertEquals("Learn Mockito", response.getTitle());
        assertEquals("Write my first Mockito test", response.getDescription());
        assertFalse(response.isCompleted());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldReturnAllTasks() {
        Task task = new Task("Learn Mockito", "Write my first Mockito test");
        task.setId(1L);

        when(taskRepository.findAll())
                .thenReturn(List.of(task));

        List<TaskResponse> responses = taskService.getAllTasks();

        TaskResponse response = responses.get(0);

        assertEquals(1, responses.size());
        assertEquals(1L, response.getId());
        assertEquals("Learn Mockito", response.getTitle());
        assertEquals("Write my first Mockito test", response.getDescription());
        assertFalse(response.isCompleted());

        verify(taskRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTasks() {
        when(taskRepository.findAll())
                .thenReturn(List.of());

        List<TaskResponse> responses = taskService.getAllTasks();

        assertTrue(responses.isEmpty());

        verify(taskRepository).findAll();
    }

    @Test
    void shouldReturnTaskById() {
        Task task = new Task("Learn Mockito", "Write my first Mockito test");
        task.setId(1L);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Learn Mockito", response.getTitle());
        assertEquals("Write my first Mockito test", response.getDescription());
        assertFalse(response.isCompleted());

        verify(taskRepository).findById(1L);
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task("Old title", "Old description");
        task.setId(1L);
        TaskRequest request = new TaskRequest("New title","New description");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskResponse response = taskService.updateTask(1L,request);

        assertEquals(1L, response.getId());
        assertEquals("New title", response.getTitle());
        assertEquals("New description", response.getDescription());
        assertFalse(response.isCompleted());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldCompleteTask() {
        Task task = new Task("Learn Mockito", "Write my first Mockito test");
        task.setId(1L);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskResponse response = taskService.completeTask(1L);

        assertEquals(1L, response.getId());
        assertEquals("Learn Mockito", response.getTitle());
        assertEquals("Write my first Mockito test", response.getDescription());
        assertTrue(response.isCompleted());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task("Learn Mockito", "Write my first Mockito test");
        task.setId(1L);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        taskService.deleteTaskById(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999L)
        );

        assertEquals("Task not found with id: 999", exception.getMessage());
        verify(taskRepository).findById(999L);
    }
}
