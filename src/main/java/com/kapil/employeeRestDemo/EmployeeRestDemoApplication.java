package com.kapil.employeeRestDemo;

import com.kapil.employeeRestDemo.model.JwtPayload;
import com.kapil.employeeRestDemo.service.JwtServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;

@SpringBootApplication
public class  EmployeeRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRestDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run() {
		return args -> {
			System.out.println("Application started!");



		};
	}
}

