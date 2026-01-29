package com.kapil.employeeRestDemo.exception;

public class InvalidRoleException extends RuntimeException{
    private String errorMessage;
    private int errorStatus;

    public InvalidRoleException(String errorMessage, int errorStatus){
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
