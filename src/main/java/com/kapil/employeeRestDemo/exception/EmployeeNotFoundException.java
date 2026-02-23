package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends BaseException{

    public EmployeeNotFoundException() {
        super("Employee Not Found", HttpStatus.NOT_FOUND);
    }
}
