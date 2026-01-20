package com.kapil.employeeRestDemo.rest;

import com.kapil.employeeRestDemo.dto.EmailDTO;
import com.kapil.employeeRestDemo.model.Employee;
import com.kapil.employeeRestDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> listEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable int id){
        return ResponseEntity.ok(employeeService.findEmployee(id));
    }

    @PostMapping("/employees")
    public void addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<String> removeAllEmployee(){
        employeeService.deleteAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body("All employees removed successfully");
    }

    @DeleteMapping("/employees/{id}")
    public void removeEmployee(@PathVariable int id){
        employeeService.removeEmployee(id);
    }

    @DeleteMapping(value = "/employees", params = "lastName")
    public ResponseEntity<String> removeEmployeeByLastName(@RequestParam String lastName){
        return ResponseEntity.status(HttpStatus.OK)
                .body("No of rows deleted successfully: "+employeeService.removeEmployeeByLastName(lastName));
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeEmail(@PathVariable int id, @RequestBody EmailDTO emailDTO){
        return
                ResponseEntity.ok(employeeService
                        .updateEmailOfEmployee(id,emailDTO.getEmail()));
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody
    Map<String,Object> patchPayload){

        return ResponseEntity.ok(employeeService
                .partialUpdate(employeeId, patchPayload));

    }
}
