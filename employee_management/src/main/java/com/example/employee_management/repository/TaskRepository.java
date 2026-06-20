package com.example.employee_management.repository;

import com.example.employee_management.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAssigneeIdOrderByDueDateAsc(String assigneeId);
}
