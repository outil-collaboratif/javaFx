package com.example.gestionsalledesport.models;

import java.math.BigInteger;

public class Admin extends User{
    private BigInteger adminId;
    private String password;

    public Admin(Long id, String nom, String prenom, String email, String birthday, BigInteger adminId,String password) {
        super(id, nom, prenom, email, birthday);
        this.adminId = adminId;
        this.password = password;
    }

    public BigInteger getAdminId() {
        return adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdminId(BigInteger adminId) {
        this.adminId = adminId;
    }
}
