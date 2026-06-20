package com.example.employee_management.controller;

import com.example.employee_management.model.Attendance;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.AttendanceRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import com.example.employee_management.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepo;
    private final EmployeeRepository employeeRepo;

    public AttendanceController(AttendanceRepository attendanceRepo, EmployeeRepository employeeRepo) {
        this.attendanceRepo = attendanceRepo;
        this.employeeRepo = employeeRepo;
    }

    @PostMapping("/mark/{employeeId}")
    public ResponseEntity<ApiResponse<Attendance>> markAttendance(@PathVariable String employeeId) {

        Employee emp = employeeRepo.findById(employeeId).orElse(null);

        if (emp == null) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Employee not found", null));
        }

        List<Attendance> existing = attendanceRepo
                .findByEmployeeIdAndDate(employeeId, LocalDate.now().toString());

        if (!existing.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Attendance already marked", existing.get(0))
            );
        }

        // CREATE NEW
        Attendance attendance = new Attendance(
                emp.getId(),
                emp.getName(),
                LocalDateTime.now(),
                "Present"
        );

        attendance.setDate(LocalDate.now().toString());

        Attendance saved = attendanceRepo.save(attendance);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Attendance marked successfully", saved)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendance>>> getAllAttendance() {
        List<Attendance> list = attendanceRepo.findAll();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Attendance fetched", list)
        );
    }

    // GET BY EMPLOYEE
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<List<Attendance>>> getByEmployee(@PathVariable String employeeId) {
        List<Attendance> list = attendanceRepo.findByEmployeeId(employeeId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Attendance fetched", list)
        );
    }
}