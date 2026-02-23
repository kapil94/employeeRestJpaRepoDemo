package com.kapil.employeeRestDemo.rest;

import com.kapil.employeeRestDemo.dto.TokenDto;
import com.kapil.employeeRestDemo.dto.UserRecord;
import com.kapil.employeeRestDemo.service.CustomUserDetailsService;
import com.kapil.employeeRestDemo.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private JwtServiceImpl jwtServiceImpl;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService, JwtServiceImpl jwtServiceImpl){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtServiceImpl = jwtServiceImpl;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserRecord record){
        customUserDetailsService.addUser(record);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody TokenDto token){
        return jwtServiceImpl.generateToken(token.username(),token.roles());
    }
}
