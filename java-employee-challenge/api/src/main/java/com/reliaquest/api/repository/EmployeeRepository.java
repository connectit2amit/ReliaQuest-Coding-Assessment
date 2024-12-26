package com.reliaquest.api.repository;

import com.reliaquest.api.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private final Map<String, Employee> employeeDatabase = new LinkedHashMap<>();

    public List<Employee> findAll() {
        return new ArrayList<>(employeeDatabase.values());
    }

    public Optional<Employee> findById(String id) {
        return Optional.ofNullable(employeeDatabase.get(id));
    }

    public List<Employee> findByNameContains(String searchString) {
        return employeeDatabase.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        employeeDatabase.put(employee.getId(), employee);
        return employee;
    }

    public boolean deleteById(String id) {
        return employeeDatabase.remove(id) != null;
    }

    public OptionalInt findHighestSalary() {
        return employeeDatabase.values().stream().mapToInt(Employee::getSalary).max();
    }

    public List<String> findTopTenHighestEarningNames() {
        return employeeDatabase.values().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}