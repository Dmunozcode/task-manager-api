package com.daniel.task.manager.api.repository;

import com.daniel.task.manager.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository <Task, Long> {
}
