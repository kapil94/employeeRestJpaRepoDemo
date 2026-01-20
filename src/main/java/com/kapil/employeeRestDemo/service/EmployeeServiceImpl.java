package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.dao.EmployeeRepository;
import com.kapil.employeeRestDemo.exception.EmployeeIdNotRequiredException;
import com.kapil.employeeRestDemo.exception.EmployeeNotFoundException;
import com.kapil.employeeRestDemo.exception.EmployeeWithLastNameNotFoundException;
import com.kapil.employeeRestDemo.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ObjectMapper objectMapper){

        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployee(int id) {

        Optional<Employee> optional = employeeRepository.findById(id);

        return optional.map(emp-> {
                             optional.get();
                            return emp;
                        }
                )
                .orElseThrow( ()-> new EmployeeNotFoundException("Employee with id "+id+" does not exist", HttpStatus.NOT_FOUND.value()));
    }

    @Transactional
    @Override
    public void removeEmployee(int id) {

        Optional<Employee> optional = employeeRepository.findById(id);
        optional.ifPresentOrElse(employeeRepository::delete,
                () -> {
                    throw new EmployeeNotFoundException("Employee with id " + id + " does not exist", HttpStatus.NOT_FOUND.value());
                }
        );
    }

    @Transactional
    @Override
    public int removeEmployeeByLastName(String lastName) {

        int deletedRows= employeeRepository.deleteEmployeeByLastName(lastName);

        if(deletedRows==0){
            throw new EmployeeWithLastNameNotFoundException("Employee with lastName :"+lastName+" is not found", HttpStatus.NOT_FOUND.value());
        }

        return deletedRows;
    }

    @Transactional
    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Transactional
    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public Employee updateEmailOfEmployee(int id, String email) {
        Optional<Employee> optional = employeeRepository.findById(id);

        return optional.map(employee->
                {
                    employee.setEmail(email);
                    return employee;
                    }).orElseThrow(()-> {
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exist", HttpStatus.NOT_FOUND.value());
        });
    }

    @Transactional
    @Override
    public Employee partialUpdate(int id, Map<String,Object> patchPayload) {
        Optional<Employee> optional = employeeRepository.findById(id);

        if (patchPayload.containsKey("id")) {
            throw new EmployeeIdNotRequiredException("Employee id not required in payload", HttpStatus.BAD_REQUEST.value());
        }

        return optional.map(employee -> {
            objectMapper.updateValue(optional.get(), patchPayload);
            return employee;
        }).orElseThrow(()-> new EmployeeNotFoundException("Employee with id " + id + " does not exist", HttpStatus.NOT_FOUND.value()));
    }
}
