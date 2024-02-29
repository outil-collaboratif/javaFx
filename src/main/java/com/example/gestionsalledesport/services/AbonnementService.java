package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.AbonnementType;
import com.example.gestionsalledesport.models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService {
    private final DAO dao;

    public AbonnementService() {
        this.dao = new DAO();
    }

    public void createAbonnementTable() {
        String createAbonnementTableSQL = "CREATE TABLE IF NOT EXISTS abonnements (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "date VARCHAR(20) NOT NULL," +
                "duree VARCHAR(50) NOT NULL," +
                "tarif DOUBLE NOT NULL," +
                "user_id INT NOT NULL," +
                "type ENUM('STUDENT', 'NORMAL') NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")";
        dao.executeQuery(createAbonnementTableSQL);
        System.out.println("Abonnements table created successfully.");
    }


    public void insertAbonnement(Abonnement abonnement) {
        if (abonnement == null) {
            throw new IllegalArgumentException("Abonnement object cannot be null");
        }

        String insertAbonnementSQL = "INSERT INTO abonnements (date, duree, tarif, user_id, type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertAbonnementSQL)) {
            preparedStatement.setDate(1, Date.valueOf((abonnement.getDate())));
            preparedStatement.setString(2, abonnement.getDurée());
            preparedStatement.setDouble(3, abonnement.getTarif());
            preparedStatement.setLong(4, abonnement.getUser().getId());
            preparedStatement.setString(5, abonnement.getType().name()); // Store the enum name as a string

            // Check if User is not null before accessing its properties
            if (abonnement.getUser() != null) {
                preparedStatement.setLong(4, abonnement.getUser().getId());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            preparedStatement.executeUpdate();
            System.out.println("Abonnement inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update operation
    public void updateAbonnement(Abonnement abonnement) {
        // Previous code...

        String updateAbonnementSQL = "UPDATE abonnements SET date=?, duree=?, tarif=?, user_id=?, type=? WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(updateAbonnementSQL)) {
            preparedStatement.setDate(1, Date.valueOf(abonnement.getDate()));
            preparedStatement.setString(2, abonnement.getDurée());
            preparedStatement.setDouble(3, abonnement.getTarif());
            preparedStatement.setLong(4, abonnement.getUser().getId());
            preparedStatement.setString(5, abonnement.getType().name()); // Store the enum name as a string
            preparedStatement.setLong(6, abonnement.getId());

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
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String duree = resultSet.getString("duree");
                double tarif = resultSet.getDouble("tarif");
                long userId = resultSet.getLong("user_id");
                AbonnementType type = AbonnementType.valueOf(resultSet.getString("type"));

                // Construct User object
                User user = new User(
                        userId,
                        resultSet.getString("users.nom"),
                        resultSet.getString("users.prenom"),
                        resultSet.getString("users.email"),
                        resultSet.getString("users.birthday")
                );

                Abonnement abonnement = new Abonnement(id, date, duree, tarif, user,type);

                abonnements.add(abonnement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return abonnements;
    }
}

