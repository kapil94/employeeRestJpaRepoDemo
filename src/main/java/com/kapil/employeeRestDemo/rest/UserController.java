package com.kapil.employeeRestDemo.rest;

import com.kapil.employeeRestDemo.dto.UserRecord;
import com.kapil.employeeRestDemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserRecord record){
        customUserDetailsService.addUser(record);
    }

}
