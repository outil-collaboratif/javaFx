package com.example.gestionsalledesport.models;

import java.util.List;

public class Cours {
    private Long id;
    private String libelle;
    private String description;
    private String duree;
    private String date;
    private User coach; // Change Coach to User
    private List<User> participants;

    public Cours(Long id, String libelle, String description, String duree, String date, User coach, List<User> participants) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.duree = duree;
        this.date = date;
        this.coach = coach;
        this.participants = participants;
    }

    // Getters and setters for coach and participants
    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    // Other getters and setters for existing fields
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
