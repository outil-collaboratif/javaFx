package com.example.gestionsalledesport.models;

import java.util.Date;

public class GymFacility {
    private int id;
    private String name;
    private FacilityType type; // Enum representing facility types
    private boolean available;
    private Date unavailableDate; // Date when the facility is not available

    public GymFacility(int id, String name, FacilityType type, boolean available, Date unavailableDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.available = available;
        this.unavailableDate = unavailableDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FacilityType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getUnavailableDate() {
        return unavailableDate;
    }

    // Enum representing facility types
    public enum FacilityType {
        WORKOUT_AREA,
        POOL,
        DANCE_STUDIO
    }
}
