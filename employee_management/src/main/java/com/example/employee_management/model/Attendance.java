package com.example.employee_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;

    private String employeeId;
    private String employeeName;
    private LocalDateTime timestamp;
    private String status; // Present / Absent

    @Field("date")
    private String date;

    public Attendance() {}

    public Attendance(String employeeId, String employeeName, LocalDateTime timestamp, String status) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getId() { return id; }
    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setStatus(String status) { this.status = status; }
}