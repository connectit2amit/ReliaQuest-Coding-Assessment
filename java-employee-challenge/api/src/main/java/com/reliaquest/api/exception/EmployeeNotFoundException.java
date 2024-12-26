package com.reliaquest.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeNotFoundException extends RuntimeException {
    private final String message;
    private final int errorCode;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.message = message;
        this.errorCode = 404; // Default error code
    }
}
