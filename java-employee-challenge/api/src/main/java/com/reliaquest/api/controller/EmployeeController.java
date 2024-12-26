package com.reliaquest.api.controller;

import com.reliaquest.api.exception.*;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.dto.EmployeeDTO;
import com.reliaquest.api.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController implements IEmployeeController<Employee, EmployeeDTO> {

    private final EmployeeServiceImpl employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee); // Return 200 OK with employee data
        } catch (EmployeeNotFoundException ex) {
            // This should now be handled by the GlobalExceptionHandler
            throw ex;  // Rethrow to be handled by the global exception handler
        } catch (Exception ex) {
            // Any other exceptions will also be caught by the global exception handler
            throw new EmployeeServiceException("Unexpected error occurred while fetching employee data.", ex);
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@Valid EmployeeDTO employeeDTO) {
        try {
            return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
        } catch (Exception ex) {
            throw new EmployeeCreationException("Unexpected error occurred while creating employee.");
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        try {
            boolean deleted = employeeService.deleteEmployeeById(id);
            return deleted ? ResponseEntity.ok("Employee deleted successfully")
                    : ResponseEntity.notFound().build();
        } catch (EmployeeNotFoundException ex) {
            throw ex;  // Rethrow to be handled by the global exception handler
        } catch (EmployeeDeletionException ex) {
            throw new EmployeeDeletionException("Unexpected error occurred while deleting employee.");
        }
    }
}
