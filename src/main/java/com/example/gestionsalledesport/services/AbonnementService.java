package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
        if (abonnement == null) {
            throw new IllegalArgumentException("Abonnement object cannot be null");
        }

        String insertAbonnementSQL = "INSERT INTO abonnements (date, duree, tarif, user_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertAbonnementSQL)) {
            preparedStatement.setString(1, abonnement.getDate());
            preparedStatement.setString(2, abonnement.getDurée());
            preparedStatement.setDouble(3, abonnement.getTarif());

            // Check if User is not null before accessing its properties
            if (abonnement.getUser() != null) {
                preparedStatement.setLong(4, abonnement.getUser().getId());
            } else {
                // Handle the case where User is null (e.g., set user_id to a default value)
                preparedStatement.setNull(4, Types.NULL);
            }

            preparedStatement.executeUpdate();
            System.out.println("Abonnement inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateAbonnement(Abonnement abonnement) {
        String updateAbonnementSQL = "UPDATE abonnements SET date=?, duree=?, tarif=?, user_id=? WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(updateAbonnementSQL)) {
            preparedStatement.setString(1, abonnement.getDate());
            preparedStatement.setString(2, abonnement.getDurée());
            preparedStatement.setDouble(3, abonnement.getTarif());
            preparedStatement.setLong(4, abonnement.getUser().getId());
            preparedStatement.setLong(5, abonnement.getId());

            preparedStatement.executeUpdate();
            System.out.println("Abonnement updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAbonnement(Abonnement abonnement) {
        String deleteAbonnementSQL = "DELETE FROM abonnements WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(deleteAbonnementSQL)) {
            preparedStatement.setLong(1, abonnement.getId());

            preparedStatement.executeUpdate();
            System.out.println("Abonnement deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Abonnement> getAllAbonnements() {
        List<Abonnement> abonnements = new ArrayList<>();
        String selectAbonnementsSQL = "SELECT * FROM abonnements INNER JOIN users ON abonnements.user_id = users.id";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectAbonnementsSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("abonnements.id");
                String date = resultSet.getString("date");
                String duree = resultSet.getString("duree");
                double tarif = resultSet.getDouble("tarif");
                long userId = resultSet.getLong("user_id");

                // Construct User object
                User user = new User(
                        userId,
                        resultSet.getString("users.nom"),
                        resultSet.getString("users.prenom"),
                        resultSet.getString("users.email"),
                        resultSet.getString("users.birthday")
                        
                );

                // Construct Abonnement object
                Abonnement abonnement = new Abonnement(id, date, duree, tarif, user);

                abonnements.add(abonnement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return abonnements;
    }



}

