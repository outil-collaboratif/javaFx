package com.example.gestionsalledesport.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StatisticsService {
    private final DAO dao;

    public StatisticsService() {
        this.dao = new DAO();
    }

    public int getTotalAbonnements() {
        String query = "SELECT COUNT(*) FROM abonnements";
        return executeCountQuery(query);
    }

    public int getActiveAbonnements() {
        String query = "SELECT COUNT(*) FROM abonnements WHERE active = true";
        return executeCountQuery(query);
    }

    public int getTotalUsers() {
        String query = "SELECT COUNT(*) FROM users";
        return executeCountQuery(query);
    }

    public int getActiveUsers() {
        String query = "SELECT COUNT(*) FROM users WHERE active = true";
        return executeCountQuery(query);
    }

    public int getTotalCoaches() {
        String query = "SELECT COUNT(*) FROM coaches";
        return executeCountQuery(query);
    }

    private int executeCountQuery(String query) {
        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isAbonnementActive(String date, int duree) {
        LocalDate abonnementDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        LocalDate expiryDate = abonnementDate.plusMonths(duree); // Assuming duree is in months

        return currentDate.isBefore(expiryDate);
    }
}

