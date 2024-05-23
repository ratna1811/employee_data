package com.example.employeemanagement.controller;


import com.example.employeemanagement.dto.EmployeeRequest;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.PhoneNumber;
import com.example.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
    @Autowired
    private EmployeeService employeeService;
    
    

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest, BindingResult bindingResult) {
    	//public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) { 
    	if (bindingResult.hasErrors()) {
             // Construct error response
    		 
             Map<String, String> errors = new HashMap<>();
             
             List<FieldError> fieldErrors = bindingResult.getFieldErrors();
             
             for (FieldError error : fieldErrors) {
            	 System.out.println("Bad Request -38");
                 errors.put(error.getField(), error.getDefaultMessage());
             }
             
             return ResponseEntity.badRequest().body(errors);
         }
    	
    	
    	Employee employee = new Employee();
    	
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDoj(employeeRequest.getDoj());
        employee.setSalary(employeeRequest.getSalary());

        List<String> phoneNumbers = employeeRequest.getPhoneNumbers();
        System.out.println("Phone Numbers from Request: " + phoneNumbers);
        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            for (String phoneNumber : phoneNumbers) {
                PhoneNumber phone = new PhoneNumber();
                phone.setPhoneNumber(phoneNumber);
                phone.setEmployee(employee); // Ensure the relationship is set
                employee.addPhoneNumber(phone);
            }
        }
        Employee savedEmployee = employeeService.saveEmployee(employee);
        
        // Clean up any phone numbers that are not associated with this employee anymore
        List<PhoneNumber> existingPhoneNumbers = savedEmployee.getPhoneNumbers();
        for (PhoneNumber existingPhone : existingPhoneNumbers) {
            if (!phoneNumbers.contains(existingPhone.getPhoneNumber())) {
                savedEmployee.removePhoneNumber(existingPhone);
            }
        }
        System.out.println("Saved Employee: " + savedEmployee.getPhoneNumbers());

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
