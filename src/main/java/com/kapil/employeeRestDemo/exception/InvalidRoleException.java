package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;

public class InvalidRoleException extends BaseException{

    public InvalidRoleException() {
        super("Invalid role passed", HttpStatus.BAD_REQUEST);
    }
}
