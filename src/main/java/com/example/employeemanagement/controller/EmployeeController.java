package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}/tax")
    public ResponseEntity<?> getEmployeeTax(@PathVariable Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        double yearlySalary = employeeService.calculateYearlySalary(employee);
        double tax = employeeService.calculateTax(employee);
        double cess = (yearlySalary > 2500000) ? (yearlySalary - 2500000) * 0.02 : 0;

        Map<String, Object> response = new HashMap<>();
        response.put("employeeCode", employee.getId());
        response.put("firstName", employee.getFirstName());
        response.put("lastName", employee.getLastName());
        response.put("yearlySalary", yearlySalary);
        response.put("taxAmount", tax);
        response.put("cessAmount", cess);

        return ResponseEntity.ok(response);
    }
}
