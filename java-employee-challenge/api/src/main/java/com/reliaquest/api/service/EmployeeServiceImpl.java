package com.reliaquest.api.service;

import com.reliaquest.api.exception.EmployeeDeletionException;
import com.reliaquest.api.exception.EmployeeNotFoundException;
import com.reliaquest.api.mapper.IEmployeeMapper;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.dto.EmployeeDTO;
import com.reliaquest.api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final IEmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAllEmployees() {
        // TODO: We should use pagination here.
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found");
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = employeeRepository.findByNameContains(searchString);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found with the name containing: " + searchString);
        }
        return employees;
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        return employeeRepository.findHighestSalary()
                .orElseThrow(() -> new EmployeeNotFoundException("No employee found with a salary."));
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<String> list = employeeRepository.findTopTenHighestEarningNames();
        if (list.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found");
        }
        return list;
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        // Since there are no additional fields like email or phone number for unique identification,
        // we are not performing any extra validation.
        // The employee ID is generated by us, and we don't have any other fields to verify the record's authenticity.
//        if (employeeDTO.getSalary() <= 0) {
//            throw new InvalidSalaryException("Salary must be greater than 0");
//        }
        Employee newEmployee = employeeMapper.toEntity(employeeDTO);
        return employeeRepository.save(newEmployee);
    }

    @Override
    public boolean deleteEmployeeById(String id) {
        // Ensure the employee exists by calling getEmployeeById
        getEmployeeById(id);  // This will throw EmployeeNotFoundException if not found

        // Attempt to delete the employee
        boolean deleted = employeeRepository.deleteById(id);

        // If deletion fails, throw EmployeeDeletionException
        if (!deleted) {
            throw new EmployeeDeletionException("Failed to delete employee with ID " + id);
        }
        return true; // Return true if deletion was successful
    }
}
