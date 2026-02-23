package com.kapil.employeeRestDemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AuthorityId implements Serializable {

    @Column(name="username",length = 50)
    private String username;
    @Column(name="authority",length = 50)
    private String authority;

    public AuthorityId(){}

    public AuthorityId(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public String getUsername() {
        return username;
    }
}
