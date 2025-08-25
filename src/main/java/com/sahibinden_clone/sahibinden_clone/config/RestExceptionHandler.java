// package: com.sahibinden_clone.sahibinden_clone.config (veya exception)
package com.sahibinden_clone.sahibinden_clone.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIAE(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAny(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
