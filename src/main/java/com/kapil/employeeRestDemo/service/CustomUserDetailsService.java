package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.dto.UserRecord;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService  {

    void addUser(UserRecord record);
}
