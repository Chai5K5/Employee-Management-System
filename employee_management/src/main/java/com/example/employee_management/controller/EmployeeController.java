package com.example.employee_management.controller;

import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.employee_management.repository.AttendanceRepository;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;
    private final AttendanceRepository attendanceRepo;
    public EmployeeController(EmployeeRepository repository, AttendanceRepository attendanceRepo) {
        this.repository = repository;
        this.attendanceRepo = attendanceRepo;
    }
    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee emp) {

        if (emp.getName() == null || emp.getName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Name is required", null));
        }

        if (emp.getEmail() == null || emp.getEmail().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Email is required", null));
        }

        if (emp.getPassword() == null || emp.getPassword().isEmpty()) {
            emp.setPassword("1234"); // Default password
        }

        Employee saved = repository.save(emp);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Employee created successfully", saved));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        List<Employee> list = repository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Employees fetched", list));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable String id,
            @RequestBody Employee emp) {

        Optional<Employee> existing = repository.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Employee not found", null));
        }

        emp.setId(id);
        Employee updated = repository.save(emp);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Employee updated successfully", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable String id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Employee not found", null));
        }

        repository.deleteById(id);

        attendanceRepo.deleteByEmployeeId(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Employee deleted successfully", null)
        );
    }
}