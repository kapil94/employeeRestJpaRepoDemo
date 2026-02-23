package com.kapil.employeeRestDemo.dto;

import java.util.List;

public record TokenDto(String username, List<String> roles) {
}
