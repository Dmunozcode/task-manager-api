package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.model.Task;
import com.daniel.task.manager.api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        validateTask(task);
        Task newTask = new Task(task.getTitle(), task.getDescription());
        return taskRepository.save(newTask);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).
                orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found"
                        )
                );
    }

    public void deleteTaskById(Long id) {
        Task task = getTaskById(id);

        taskRepository.delete(task);
    }

    public Task updateTask( Long id, Task updatedTask){
        validateTask(updatedTask);

        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }

    public Task completeTask(Long id){
        Task task = getTaskById(id);

        task.markAsCompleted();

        return taskRepository.save(task);
    }

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title is required");
        }

        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task description is required");
        }
    }

}
