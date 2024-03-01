package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Cours;
import com.example.gestionsalledesport.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursService {
    private final DAO dao;

    public CoursService() {
        this.dao = new DAO();
    }

    public void createCoursTable() {
        // Create 'cours' table
        String createCoursTableSQL = "CREATE TABLE IF NOT EXISTS cours (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "libelle VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "duree VARCHAR(50) NOT NULL," +
                "date VARCHAR(20) NOT NULL," +
                "coach_id INT NOT NULL," +
                "FOREIGN KEY (coach_id) REFERENCES users(id)" + // Assuming the table for users is named 'users'
                ")";
        dao.executeQuery(createCoursTableSQL);
        System.out.println("Cours table created successfully.");
    }

    public void insertCours(Cours cours) {
        if (cours == null) {
            throw new IllegalArgumentException("Cours object cannot be null");
        }

        String insertCoursSQL = "INSERT INTO cours (libelle, description, duree, date, coach_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertCoursSQL)) {
            preparedStatement.setString(1, cours.getLibelle());
            preparedStatement.setString(2, cours.getDescription());
            preparedStatement.setString(3, cours.getDuree());
            preparedStatement.setString(4, cours.getDate());
            preparedStatement.setLong(5, cours.getCoach().getId());

            preparedStatement.executeUpdate();
            System.out.println("Cours inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCours(Cours cours) {
        String updateCoursSQL = "UPDATE cours SET libelle=?, description=?, duree=?, date=?, coach_id=? WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(updateCoursSQL)) {
            preparedStatement.setString(1, cours.getLibelle());
            preparedStatement.setString(2, cours.getDescription());
            preparedStatement.setString(3, cours.getDuree());
            preparedStatement.setString(4, cours.getDate());
            preparedStatement.setLong(5, cours.getCoach().getId());
            preparedStatement.setLong(6, cours.getId());

            preparedStatement.executeUpdate();
            System.out.println("Cours updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCours(Cours cours) {
        String deleteCoursSQL = "DELETE FROM cours WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(deleteCoursSQL)) {
            preparedStatement.setLong(1, cours.getId());

            preparedStatement.executeUpdate();
            System.out.println("Cours deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cours> getAllCours() {
        List<Cours> coursList = new ArrayList<>();
        String selectCoursSQL = "SELECT * FROM cours";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectCoursSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String libelle = resultSet.getString("libelle");
                String description = resultSet.getString("description");
                String duree = resultSet.getString("duree");
                String date = resultSet.getString("date");
                long coachId = resultSet.getLong("coach_id");

                // Construct User object for coach
                User coach = new User(coachId); // Assuming you have a User class with constructor that takes ID

                Cours cours = new Cours(id, libelle, description, duree, date, coach, null); // Participants are not loaded here

                coursList.add(cours);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return coursList;
    }
}