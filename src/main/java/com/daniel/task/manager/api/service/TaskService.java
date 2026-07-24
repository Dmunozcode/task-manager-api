package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.dto.TaskRequest;
import com.daniel.task.manager.api.dto.TaskResponse;
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

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> toResponse(task))
                .toList();
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = toEntity(request);
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).
                orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found"
                        )
                );
    }

    public TaskResponse getTaskById(Long id) {
        Task task = findTaskById(id);
        return toResponse(task);
    }

    public void deleteTaskById(Long id) {
        Task task = findTaskById(id);

        taskRepository.delete(task);
    }

    public TaskResponse updateTask( Long id, TaskRequest updatedTask){
        Task existingTask = findTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());

        Task savedTask = taskRepository.save(existingTask);

        return toResponse(savedTask);
    }

    public TaskResponse completeTask(Long id){
        Task task = findTaskById(id);
        task.markAsCompleted();
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(), task.isCompleted()
        );
    }

    private Task toEntity(TaskRequest request) {
        return new Task(
                request.getTitle(), request.getDescription()
        );
    }

}
