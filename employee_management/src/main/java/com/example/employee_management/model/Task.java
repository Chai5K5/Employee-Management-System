package com.example.employee_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private boolean completed;
    
    private String assignerId;
    private String assignerName;
    
    private String assigneeId;
    private String assigneeName;
    
    private String dueDate; // e.g., "2026-04-22"
    private LocalDateTime createdAt;
    
    private String type; // "personal", "assigned"

    public Task() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getAssignerId() { return assignerId; }
    public void setAssignerId(String assignerId) { this.assignerId = assignerId; }

    public String getAssignerName() { return assignerName; }
    public void setAssignerName(String assignerName) { this.assignerName = assignerName; }

    public String getAssigneeId() { return assigneeId; }
    public void setAssigneeId(String assigneeId) { this.assigneeId = assigneeId; }

    public String getAssigneeName() { return assigneeName; }
    public void setAssigneeName(String assigneeName) { this.assigneeName = assigneeName; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
