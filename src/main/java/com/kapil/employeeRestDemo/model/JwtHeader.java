package com.kapil.employeeRestDemo.model;

public class JwtHeader {

    private String alg;
    private String typ;

    public JwtHeader(){}

    public JwtHeader(String alg) {
        this.alg = alg;
        this.typ = "JWT";
    }

    public String getAlg() {
        return alg;
    }

    public String getTyp() {
        return typ;
    }
}
