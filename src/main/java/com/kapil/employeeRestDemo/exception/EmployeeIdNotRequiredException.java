package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;

public class EmployeeIdNotRequiredException extends BaseException{

    public EmployeeIdNotRequiredException() {
        super("Id is not required in payload", HttpStatus.BAD_REQUEST);
    }
}
