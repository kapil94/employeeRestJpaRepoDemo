package com.kapil.employeeRestDemo.dao;

import com.kapil.employeeRestDemo.model.AuthorityEntity;
import com.kapil.employeeRestDemo.model.AuthorityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, AuthorityId> {

    @Query("Select auth from AuthorityEntity auth where auth.user.username=:username")
    List<AuthorityEntity> findAuthorityByUserName(@Param("username") String username);
}
