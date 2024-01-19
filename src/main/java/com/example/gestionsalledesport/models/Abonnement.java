package com.example.gestionsalledesport.models;

public class Abonnement {
    private Long id;

    private String date;

    public Abonnement(Long id, String date, String durée, double tarif, User user) {
        this.id = id;
        this.date = date;
        this.durée = durée;
        this.tarif = tarif;
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
