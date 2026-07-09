package com.daniel.task.manager.api.service;

import com.daniel.task.manager.api.model.Task;
import org.springframework.stereotype.Service;

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
        return null;
    }

    public void deleteTaskById(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
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
        return null;
    }

    public Task completeTask(Long id){
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.markAsCompleted();
                return task;
            }
        }
        return null;
    }

}
