package com.daniel.task.manager.api.controller;

import com.daniel.task.manager.api.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        Task newTask = new Task(nextId, task.getTitle(), task.getDescription());
        tasks.add(newTask);
        nextId++;
        return newTask;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        for (Task task : tasks) {
            if(task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

}
