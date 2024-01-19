package com.example.gestionsalledesport.models;

public class Coach extends User{
    private String specialite;
    public Coach(Long id, String nom, String prenom, String email, String birthday, String specialité) {
        super(id, nom, prenom, email, birthday);
        this.specialite = specialité;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
