package com.dsp.caesar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Catches validation errors and returns a clean JSON error with HTTP 400
 * instead of Spring's default Whitelabel error page.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles @Valid failures (blank text, shift out of range, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        StringBuilder messages = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            if (!messages.isEmpty()) {
                messages.append("; ");
            }
            messages.append(err.getField()).append(": ").append(err.getDefaultMessage());
        });

        Map<String, String> body = new HashMap<>();
        body.put("error", messages.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Catch-all for anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}



