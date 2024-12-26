package com.reliaquest.api.exception;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ExceptionHandlerStrategy {
    ResponseEntity<Map<String, Object>> handle(Exception ex);
}
