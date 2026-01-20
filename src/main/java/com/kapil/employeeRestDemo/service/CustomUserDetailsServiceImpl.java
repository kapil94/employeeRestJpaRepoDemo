package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.dao.AuthorityRepository;
import com.kapil.employeeRestDemo.dao.UserRepository;
import com.kapil.employeeRestDemo.dto.UserRecord;
import com.kapil.employeeRestDemo.model.AuthorityEntity;
import com.kapil.employeeRestDemo.model.AuthorityId;
import com.kapil.employeeRestDemo.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(record.username());
        userEntity.setPassword(passwordEncoder.encode(record.password()));
        userEntity.setEnabled(record.enabled());

        userRepository.save(userEntity);
        AuthorityEntity authorityEntity = new AuthorityEntity();

        authorityEntity.setUser(userEntity);
        authorityEntity.setId(new AuthorityId(record.username(), record.role().name()));

        authorityRepository.save(authorityEntity);
    }
}
