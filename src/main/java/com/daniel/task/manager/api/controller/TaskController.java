package com.daniel.task.manager.api.controller;

import com.daniel.task.manager.api.dto.TaskRequest;
import com.daniel.task.manager.api.dto.TaskResponse;
import com.daniel.task.manager.api.model.Task;
import com.daniel.task.manager.api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody TaskRequest task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updatedTask) {
        return taskService.updateTask(id,updatedTask);
    }

    @PatchMapping("/{id}/complete")
    public TaskResponse completeTask(@PathVariable Long id) {
        return taskService.completeTask(id);
    }
}
