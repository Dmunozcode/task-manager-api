package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task createTask(Task task) {
        Task newTask = new Task(nextId, task.getTitle(), task.getDescription());
        tasks.add(newTask);
        nextId++;
        return newTask;
    }

    public Task getTaskById(Long id) {
        for (Task task : tasks) {
            if(task.getId().equals(id)) {
                return task;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    public void deleteTaskById(Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }

    public Task updateTask( Long id, Task updatedTask){
        for (Task task : tasks){
            if (task.getId().equals(id)){
                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    public Task completeTask(Long id){
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.markAsCompleted();
                return task;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

}
