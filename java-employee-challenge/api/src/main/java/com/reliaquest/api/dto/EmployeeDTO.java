package com.reliaquest.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "Name must not be null, empty, or blank")
    private String name;

    @Min(value = 1, message = "Salary must be greater than or equal to 0")
    private int salary;
}