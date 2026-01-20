package com.kapil.employeeRestDemo.dto;

public record UserRecord(String username, String password,boolean enabled, ROLE role) {
}
