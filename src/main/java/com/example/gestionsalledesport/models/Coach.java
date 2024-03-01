package com.example.gestionsalledesport.models;

import java.util.List;

public class Coach {
    private Long id;
    private String specialty;
    private String bio;
    private String availability;
    private List<Cours> courses; 

    public Coach(Long id, String specialty, String bio, String availability, List<Cours> courses) {
        this.id = id;
        this.specialty = specialty;
        this.bio = bio;
        this.availability = availability;
        this.courses = courses;
    }

    public Coach(Long id, String coach, String yoga, String monday, String stretching) {
    }

    public Coach(String specialty, String bio, String availability, long courseId) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public CharSequence getCourses() {
        return (CharSequence) courses;
    }

    public void setCourses(List<Cours> courses) {
        this.courses = courses;
    }
    
}
