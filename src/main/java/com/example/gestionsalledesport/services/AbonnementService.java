package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AbonnementService {
    private final DAO dao;

    public AbonnementService() {
        this.dao = new DAO();
    }

    public void createAbonnementTable() {
        // Create 'abonnements' table
        String createAbonnementTableSQL = "CREATE TABLE IF NOT EXISTS abonnements (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "date VARCHAR(20) NOT NULL," +
                "duree VARCHAR(50) NOT NULL," +
                "tarif DOUBLE NOT NULL," +
                "user_id INT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")";
        dao.executeQuery(createAbonnementTableSQL);
        System.out.println("Abonnements table created successfully.");
    }

    public void insertAbonnement(Abonnement abonnement) {
        String insertAbonnementSQL = "INSERT INTO abonnements (date, duree, tarif, user_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertAbonnementSQL)) {
            preparedStatement.setString(1, abonnement.getDate());
            preparedStatement.setString(2, abonnement.getDurée());
            preparedStatement.setDouble(3, abonnement.getTarif());
            preparedStatement.setLong(4, abonnement.getUser().getId());

            preparedStatement.executeUpdate();
            System.out.println("Abonnement inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        AbonnementService abonnementService = new AbonnementService();

        abonnementService.createAbonnementTable();

        User exampleUser = new User(1L, "John", "Doe", "john.doe@example.com", "1990-01-01");

        // Example Abonnement
        Abonnement exampleAbonnement = new Abonnement(
                null,
                "2024-01-20",
                "1 Month",
                29.99,
                exampleUser
        );

        // Insert the example Abonnement
        abonnementService.insertAbonnement(exampleAbonnement);
    }

}

