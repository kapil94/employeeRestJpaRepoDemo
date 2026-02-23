package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

     List<Employee> findAllEmployees();
     Employee findEmployee(int id);
     void removeEmployee(int id);
     int removeEmployeeByLastName(String lastName);
     void deleteAllEmployees();
     void addEmployee(Employee employee);
     Employee updateEmailOfEmployee(int id, String email);
     Employee partialUpdate(int id, Map<String,Object> patchPayload);
}
