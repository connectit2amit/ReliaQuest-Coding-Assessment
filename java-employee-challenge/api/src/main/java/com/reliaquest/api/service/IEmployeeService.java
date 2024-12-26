package com.reliaquest.api.service;

import com.reliaquest.api.dto.EmployeeDTO;
import com.reliaquest.api.model.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    List<Employee> getEmployeesByNameSearch(String searchString);

    Integer getHighestSalaryOfEmployees();

    List<String> getTopTenHighestEarningEmployeeNames();

    Employee createEmployee(EmployeeDTO employeeInput);

    boolean deleteEmployeeById(String id);
}
