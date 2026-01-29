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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.InvalidRoleValueException;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService, CustomUserDetailsService {

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

    @Override
    public void addUser(UserRecord record) {

            ROLE role = parseRole(record.role().name());
            isUserAvailable(record.username());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserEntity userEntity = new UserEntity();

            userEntity.setUsername(record.username());
            userEntity.setPassword(passwordEncoder.encode(record.password()));
            userEntity.setEnabled(record.enabled());

            userRepository.save(userEntity);
            AuthorityEntity authorityEntity = new AuthorityEntity();

            authorityEntity.setUser(userEntity);
            authorityEntity.setId(new AuthorityId(record.username(), role.name()));

            authorityRepository.save(authorityEntity);
    }

    private ROLE parseRole(String role){
        try {
            return ROLE.valueOf(role);
        } catch (IllegalArgumentException ex){
            throw new InvalidRoleException("Invalid role passed ", HttpStatus.BAD_REQUEST.value());
        }
    }

    private void isUserAvailable(String user){

        if(userRepository.findUserByUserName(user)!=null){
            throw new DuplicateUserException("User is already added",HttpStatus.BAD_REQUEST.value());
        }
    }
}
