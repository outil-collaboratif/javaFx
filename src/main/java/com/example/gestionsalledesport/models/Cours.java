package com.example.gestionsalledesport.models;

public class Cours {
    private Long id;
    private String libelle;
    private String description;
    private String duree;
    private String date;

    public Cours(Long id, String libelle, String description, String duree, String date) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.duree = duree;
        this.date = date;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
