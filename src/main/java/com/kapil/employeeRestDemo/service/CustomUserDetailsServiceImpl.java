package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.dao.AuthorityRepository;
import com.kapil.employeeRestDemo.dao.UserRepository;
import com.kapil.employeeRestDemo.dto.ROLE;
import com.kapil.employeeRestDemo.dto.UserRecord;
import com.kapil.employeeRestDemo.exception.DuplicateUserException;
import com.kapil.employeeRestDemo.exception.InvalidRoleException;
import com.kapil.employeeRestDemo.model.AuthorityEntity;
import com.kapil.employeeRestDemo.model.AuthorityId;
import com.kapil.employeeRestDemo.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity userEntity = userRepository.findUserByUserName(username);

        if(userEntity==null){
            throw new RuntimeException("User not found");
        }

        List<GrantedAuthority> authorities = authorityRepository
                .findAuthorityByUserName(username)
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getId().getAuthority()))
                .collect(Collectors.toList());

        System.out.println("role:"+authorities);
        System.out.println("username: "+userEntity.getUsername()+" password: "+userEntity.getPassword());

        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .disabled(!userEntity.getEnabled())
                .build();
    }
}
