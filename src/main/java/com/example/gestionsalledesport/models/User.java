package com.example.gestionsalledesport.models;

public class User {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private  String birthday;

    public User(Long id, String nom, String prenom, String email, String birthday) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
