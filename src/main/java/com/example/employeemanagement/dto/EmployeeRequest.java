package com.example.employeemanagement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeRequest {
	@NotBlank(message = "First name is mandatory")
	private String firstName;
	@NotBlank(message = "Last name is mandatory")
    private String lastName;
	
	@Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
	
    private List<String> phoneNumbers = new ArrayList<>(); // List of strings
    
    @NotNull(message = "Date of joining is mandatory")
    private LocalDate doj;
    
    @NotNull(message = "Salary is mandatory")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
