package com.exmaple.coding_test.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        log.error("Exception occurred: ", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Exception occurred: ", e);
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldErrors().stream().map(ex -> ex.getDefaultMessage()).toList());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
