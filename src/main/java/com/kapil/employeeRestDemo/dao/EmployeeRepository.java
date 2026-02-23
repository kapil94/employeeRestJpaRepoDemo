package com.kapil.employeeRestDemo.dao;

import com.kapil.employeeRestDemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Query("Delete from Employee e where e.lastName=:lastName")
    int deleteEmployeeByLastName(@Param("lastName") String lastName);
}
