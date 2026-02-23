package com.kapil.employeeRestDemo.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class JwtPayload {

    private String sub;
    private List<String> role;
    private long iat;
    private long exp;

    public JwtPayload(){}

    public JwtPayload(String sub, List<String> role, long iat, long exp) {
        this.sub = sub;
        this.role = role;
        this.iat = iat;
        this.exp = exp;
    }

    public String getSub() {
        return sub;
    }

    public List<String> getRole() {
        return role;
    }

    public long getIat() {
        return iat;
    }

    public long getExp() {
        return exp;
    }
}
