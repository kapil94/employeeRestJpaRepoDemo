package com.kapil.employeeRestDemo.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

    private final HttpStatus errorStatus;

    public BaseException(String message, HttpStatus errorStatus){
        super(message);
        this.errorStatus = errorStatus;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

}
