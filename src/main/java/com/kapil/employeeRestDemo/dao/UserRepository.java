package com.kapil.employeeRestDemo.dao;

import com.kapil.employeeRestDemo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity,String> {

    @Query("Select u from UserEntity u where u.username=:username")
    UserEntity findUserByUserName(@Param("username") String username);
}
