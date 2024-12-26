package com.reliaquest.api.mapper;

import com.reliaquest.api.dto.EmployeeDTO;
import com.reliaquest.api.id.IDGenerator;
import com.reliaquest.api.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements IEmployeeMapper {
    @Override
    public Employee toEntity(EmployeeDTO dto) {
        return new Employee(IDGenerator.getUniqueId(), dto.getName(), dto.getSalary());
    }

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

}
