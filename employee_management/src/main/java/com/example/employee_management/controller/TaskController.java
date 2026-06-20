package com.example.employee_management.controller;

import com.example.employee_management.model.Task;
import com.example.employee_management.repository.TaskRepository;
import com.example.employee_management.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Title is required", null));
        }
        
        Task saved = repository.save(task);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Task created", saved));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksForEmployee(@PathVariable String employeeId) {
        List<Task> tasks = repository.findByAssigneeIdOrderByDueDateAsc(employeeId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tasks fetched", tasks));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Task>> toggleTaskCompletion(@PathVariable String id) {
        Optional<Task> existing = repository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Task not found", null));
        }

        Task task = existing.get();
        task.setCompleted(!task.isCompleted());
        Task updated = repository.save(task);

        return ResponseEntity.ok(new ApiResponse<>(true, "Task toggled", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Task not found", null));
        }

        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task deleted", null));
    }
}
