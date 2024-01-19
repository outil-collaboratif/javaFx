package com.example.gestionsalledesport.models;

import java.math.BigInteger;

public class Admin extends User{
    private BigInteger adminId;

    public Admin(Long id, String nom, String prenom, String email, String birthday, BigInteger adminId) {
        super(id, nom, prenom, email, birthday);
        this.adminId = adminId;
    }

    public BigInteger getAdminId() {
        return adminId;
    }

    public void setAdminId(BigInteger adminId) {
        this.adminId = adminId;
    }
}
