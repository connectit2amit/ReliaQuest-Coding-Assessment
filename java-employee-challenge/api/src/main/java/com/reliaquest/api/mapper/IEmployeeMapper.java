package com.reliaquest.api.mapper;

import com.reliaquest.api.dto.EmployeeDTO;
import com.reliaquest.api.model.Employee;

public interface IEmployeeMapper {
    Employee toEntity(EmployeeDTO dto);
    EmployeeDTO toDTO(Employee employee);
}
