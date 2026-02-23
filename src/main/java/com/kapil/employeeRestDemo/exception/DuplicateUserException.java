package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;

public class DuplicateUserException extends BaseException {

    public DuplicateUserException() {
        super("User already exist!", HttpStatus.BAD_REQUEST);
    }
}
