package com.example.employee_management.controller;

import com.example.employee_management.security.JwtUtil;
import com.example.employee_management.dto.ApiResponse;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    private final JwtUtil jwtUtil;
    private final EmployeeRepository employeeRepository;

    // 🔥 Inject here
    public AuthController(JwtUtil jwtUtil, EmployeeRepository employeeRepository) {
        this.jwtUtil = jwtUtil;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        // Admin login
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken("admin");
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", Map.of("token", token, "role", "admin")));
        }

        // Employee login (using email as username)
        Optional<Employee> empOpt = employeeRepository.findByEmail(username);
        if (empOpt.isPresent()) {
            Employee emp = empOpt.get();
            if (emp.getPassword() != null && emp.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(username); // Can put email in token
                return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", Map.of(
                        "token", token, 
                        "role", "employee",
                        "employeeId", emp.getId()
                )));
            }
        }

        return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid credentials", null));
    }
}