package com.kapil.employeeRestDemo;

import com.kapil.employeeRestDemo.model.JwtPayload;
import com.kapil.employeeRestDemo.service.JwtServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;

@SpringBootApplication
public class  EmployeeRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRestDemoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run() {
//		return args -> {
//			System.out.println("Application started!");
//
//			JwtServiceImpl jwtServiceImpl = new JwtServiceImpl();
//
//			System.out.println("initial token:"+ jwtServiceImpl.generateToken());
//
//			String[] encodedToken = jwtServiceImpl.generateToken().split("\\.");
//
//			String decodePayload = new String( Base64
//					.getDecoder().decode(encodedToken[1]));
//
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			JwtPayload jwtPayload = objectMapper.readValue(decodePayload, JwtPayload.class);
//
//			System.out.println("sub: "+jwtPayload.getSub());
//			System.out.println("role: "+jwtPayload.getRole());
//			System.out.println("exp: "+jwtPayload.getExp());
//			System.out.println("iat: "+jwtPayload.getIat());
//
//			//System.out.println("payload details:"+ decodePayload);
//
//		};
}

