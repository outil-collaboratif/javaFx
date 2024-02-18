package com.example.gestionsalledesport.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.gestionsalledesport.models.GymFacility;

public class GymFacilitiesService {
    final Connection connection;

    public GymFacilitiesService() {
        this.connection = DAO.getConnection(); // Establish database connection
    }

    // List all facilities
    public List<GymFacility> getAllFacilities() throws SQLException {
        List<GymFacility> facilities = new ArrayList<>();
        String query = "SELECT * FROM gym_facilities";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                GymFacility.FacilityType type = GymFacility.FacilityType.valueOf(rs.getString("type"));
                boolean available = rs.getBoolean("available");
                Date unavailableDate = rs.getDate("unavailable_date");
                facilities.add(new GymFacility(id, name, type, available, unavailableDate));
            }
        }
        return facilities;
    }

    // Insert facility for test
    public boolean insertFacility(GymFacility facility) {
        String query = "INSERT INTO gym_facilities (name, type, available, unavailable_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, facility.getName());
            stmt.setString(2, facility.getType().toString());
            stmt.setBoolean(3, true); // Newly inserted facility is available by default
            stmt.setDate(4, facility.getUnavailableDate() != null ? new java.sql.Date(facility.getUnavailableDate().getTime()) : null);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if facility is available for a specific date
    public boolean isFacilityAvailableForDate(int id, Date rentalDate) throws SQLException {
        String query = "SELECT unavailable_date FROM gym_facilities WHERE id = ? AND unavailable_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setDate(2, new java.sql.Date(rentalDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next(); // Facility is available if no matching row found for the specified date
            }
        }
    }

    // Rent facility for a specific date
    public boolean rentFacilityForDate(int id, Date rentalDate) throws SQLException {
        // Check if facility is available for the specified date
        if (isFacilityAvailableForDate(id, rentalDate)) {
            // Insert a new row for renting the facility on the specified date
            String insertQuery = "INSERT INTO gym_facilities (id, name, type, available, unavailable_date) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, id);
                insertStmt.setString(2, getFacilityNameById(id)); // Get facility name by ID
                insertStmt.setString(3, getFacilityTypeById(id)); // Get facility type by ID
                insertStmt.setBoolean(4, false); // Set available to false
                insertStmt.setDate(5, new java.sql.Date(rentalDate.getTime())); // Set unavailable date to rental date
                int rowsInserted = insertStmt.executeUpdate();
                return rowsInserted > 0; // Facility rented successfully if rows inserted
            }
        } else {
            System.out.println("Facility is not available for the specified date.");
            return false;
        }
    }
    // Method to retrieve facility name by ID
    private String getFacilityNameById(int id) throws SQLException {
        String query = "SELECT name FROM gym_facilities WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        }
        throw new SQLException("Facility not found for ID: " + id);
    }

    // Method to retrieve facility type by ID
    private String getFacilityTypeById(int id) throws SQLException {
        String query = "SELECT type FROM gym_facilities WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("type");
                }
            }
        }
        throw new SQLException("Facility not found for ID: " + id);
    }

}
