package com.example.gestionsalledesport.models;

import java.time.LocalDate;

public class Abonnement {
    private Long id;

    private LocalDate date;
    private AbonnementType type;


    public AbonnementType getType() {
        return type;
    }

    public void setType(AbonnementType type) {
        this.type = type;
    }

    public Abonnement(Long id, LocalDate date, String durée, double tarif, User user, AbonnementType type) {
        this.id = id;
        this.date = date;
        this.durée = durée;
        this.tarif = tarif;
        this.user = user;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private String durée;
    private double tarif;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDurée() {
        return durée;
    }

    public void setDurée(String durée) {
        this.durée = durée;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Abonnement(Long id, String durée, double tarif, User user) {
        this.id = id;
        this.durée = durée;
        this.tarif = tarif;
        this.user = user;
    }
    public String getUserDetails() {
        return user != null ? "ID: " + user.getId() + ", Nom: " + user.getNom() + ", Prenom: " + user.getPrenom() + ", Email: " + user.getEmail() + ", Birthday: " + user.getBirthday() : "";
    }
}
