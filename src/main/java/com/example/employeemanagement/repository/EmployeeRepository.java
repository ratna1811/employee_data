package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagement.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
