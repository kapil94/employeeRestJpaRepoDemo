package com.kapil.employeeRestDemo.rest;

import com.kapil.employeeRestDemo.dto.TokenDto;
import com.kapil.employeeRestDemo.dto.UserRecord;
import com.kapil.employeeRestDemo.service.CustomUserDetailsService;
import com.kapil.employeeRestDemo.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private JwtServiceImpl jwtServiceImpl;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService,
                          JwtServiceImpl jwtServiceImpl,
                          AuthenticationManager authenticationManager){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtServiceImpl = jwtServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserRecord record){
        customUserDetailsService.addUser(record);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody TokenDto token){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(token.username(), token.password())
        );

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return jwtServiceImpl.generateToken(token.username(), roles);
    }
}
