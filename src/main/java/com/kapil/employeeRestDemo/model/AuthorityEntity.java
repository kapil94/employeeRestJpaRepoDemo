package com.kapil.employeeRestDemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @EmbeddedId
    private AuthorityId id;

    @MapsId("username")
    @ManyToOne
    @JoinColumn(name = "username")
    private UserEntity user;

    public AuthorityEntity() { } //

    public AuthorityEntity(UserEntity user, String authority) {
        this.user = user;
        this.id = new AuthorityId(user.getUsername(), authority);
    }

    public AuthorityId getId() { return id; }
    public UserEntity getUser() { return user; }
    public void setId(AuthorityId id) { this.id = id; }
    public void setUser(UserEntity user) { this.user = user; }
}

