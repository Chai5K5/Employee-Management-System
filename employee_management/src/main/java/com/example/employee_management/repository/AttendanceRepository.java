package com.example.employee_management.repository;

import com.example.employee_management.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    void deleteByEmployeeId(String employeeId);
    List<Attendance> findByEmployeeId(String employeeId);

    List<Attendance> findByEmployeeIdAndDate(
            String employeeId,
            String date
    );
}