package com.kapil.employeeRestDemo.exception;

public class EmployeeNotFoundException extends RuntimeException{

    private String errorMessage;
    private int errorStatus;

    public EmployeeNotFoundException(String errorMessage, int errorStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }
}
