package com.reliaquest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Common method to create error response
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, String error, HttpStatus status, Map<String, String> validationErrors) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .error(error)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        // Only add validationErrors if they are present
        errorResponse.addValidationErrorsIfPresent(validationErrors);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                "Employee Not Found",
                HttpStatus.NOT_FOUND,
                new HashMap<>() // No validation errors, provide an empty map
        );
    }

    // Generic handler for any unhandled exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildErrorResponse(
                "An unexpected error occurred. Please try again later.",
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                new HashMap<>() // No validation errors, provide an empty map
        );
    }

    // Handle EmployeeCreationException
    @ExceptionHandler({EmployeeCreationException.class, EmployeeDeletionException.class})
    public ResponseEntity<ErrorResponse> handleEmployeeCreationDeletionException(Exception ex) {
        String errorType = (ex instanceof EmployeeCreationException) ? "Employee Creation Error" : "Employee Deletion Error";
        return buildErrorResponse(
                ex.getMessage(),
                errorType,
                HttpStatus.INTERNAL_SERVER_ERROR,
                new HashMap<>() // No validation errors, provide an empty map
        );
    }

    @ExceptionHandler(EmployeeServiceException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeServiceException(EmployeeServiceException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                "Internal Service Error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                new HashMap<>() // No validation errors, provide an empty map
        );
    }

    @ExceptionHandler(InvalidSalaryException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSalaryException(InvalidSalaryException ex) {
        return buildErrorResponse(
                ex.getMessage(),
                "Invalid Salary",
                HttpStatus.BAD_REQUEST,
                new HashMap<>() // No validation errors, provide an empty map
        );
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collect all validation errors
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        return buildErrorResponse(
                "Validation failed for the provided input.",
                "Validation Error",
                HttpStatus.BAD_REQUEST,
                validationErrors  // Pass the validation errors map
        );
    }

}