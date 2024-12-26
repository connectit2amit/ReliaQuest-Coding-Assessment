package com.reliaquest.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors = new HashMap<>(); // Initialize with an empty map

    // Method to add validation errors if present
    public void addValidationErrorsIfPresent(Map<String, String> validationErrors) {
        if (validationErrors != null && !validationErrors.isEmpty()) {
            this.validationErrors = validationErrors;
        }
    }
}
