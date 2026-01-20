package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(
            EmployeeNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", ex.getErrorMessage());
        error.put("errorCode", ex.getErrorStatus());


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(EmployeeIdNotRequiredException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeIdNotRequiredException(
            EmployeeIdNotRequiredException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", ex.getErrorMessage());
        error.put("errorCode", ex.getErrorStatus());


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(EmployeeWithLastNameNotFoundException.class)
    public ResponseEntity<Map<String,Object>>
    handleEmployeeWithLastNameNotFoundException(EmployeeWithLastNameNotFoundException exception){

        Map<String,Object> error = new HashMap<>();
        error.put("errorMessage",exception.getErrorMessage());
        error.put("errorStatus", exception.getErrorStatus());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
