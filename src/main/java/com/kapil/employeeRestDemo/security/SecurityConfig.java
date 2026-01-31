package com.kapil.employeeRestDemo.security;

import com.kapil.employeeRestDemo.dto.ROLE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/user/**").hasAuthority(ROLE.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/employees/**").hasAuthority(ROLE.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT,"/api/employees/**").hasAuthority(ROLE.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH,"/api/employees/**").hasAuthority(ROLE.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasAuthority(ROLE.ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/api/employees/**").hasAnyAuthority(ROLE.ADMIN.name(), ROLE.USER.name())
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
