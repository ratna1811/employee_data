package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public double calculateTax(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double tax = 0;

        if (yearlySalary <= 250000) {
            tax = 0;
        } else if (yearlySalary <= 500000) {
            tax = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            tax = 250000 * 0.05 + (yearlySalary - 500000) * 0.10;
        } else {
            tax = 250000 * 0.05 + 500000 * 0.10 + (yearlySalary - 1000000) * 0.20;
        }

        if (yearlySalary > 2500000) {
            double cess = (yearlySalary - 2500000) * 0.02;
            tax += cess;
        }

        return tax;
    }

    public double calculateYearlySalary(Employee employee) {
        LocalDate startDate = employee.getDoj();
        LocalDate now = LocalDate.now();
        long monthsWorked = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), now.withDayOfMonth(1));
        return employee.getSalary() * monthsWorked;
    }

	public Employee findEmployeeById(Long id) {
		// TODO Auto-generated method stub
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
	    if (employeeOptional.isPresent()) {
	        return employeeOptional.get();
	    } else {
	        throw new EmployeeNotFoundException("Employee not found with id: " + id);
	    }
	}

	
}
