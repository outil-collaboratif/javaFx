package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Admin;
import com.example.gestionsalledesport.models.Coach;
import com.example.gestionsalledesport.models.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class UserService {
    private final DAO dao;

    public UserService() {
        this.dao = new DAO();
    }

    public void createUserTable() {
        // Create 'users' table
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(50) NOT NULL," +
                "prenom VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) UNIQUE NOT NULL," +
                "birthday VARCHAR(20) NOT NULL" +
                ")";
        dao.executeQuery(createUserTableSQL);
        System.out.println("User table created successfully.");

        // Create 'admins' table
        String createAdminTableSQL = "CREATE TABLE IF NOT EXISTS admins (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(50) NOT NULL," +
                "prenom VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) UNIQUE NOT NULL," +
                "birthday VARCHAR(20) NOT NULL," +
                "admin_id DECIMAL NOT NULL" + // Adjust the type based on your database
                ")";
        dao.executeQuery(createAdminTableSQL);
        System.out.println("Admin table created successfully.");

        // Create 'coaches' table
        String createCoachTableSQL = "CREATE TABLE IF NOT EXISTS coaches (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    specialty VARCHAR(100) NOT NULL," +
                "    bio TEXT," +
                "    availability VARCHAR(100)" +
                ")";
        dao.executeQuery(createCoachTableSQL);
        System.out.println("Coach table created successfully.");
    }


    public void insertUser(User user) {
        String insertUserSQL = "INSERT INTO users (nom, prenom, email, birthday) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Objects.requireNonNull(DAO.getConnection()).prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getBirthday());

            preparedStatement.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAdmin(Admin admin) {
        String insertAdminSQL = "INSERT INTO admins (nom, prenom, email, birthday, admin_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Objects.requireNonNull(DAO.getConnection()).prepareStatement(insertAdminSQL)) {
            preparedStatement.setString(1, admin.getNom());
            preparedStatement.setString(2, admin.getPrenom());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getBirthday());

            // Convert BigInteger to BigDecimal for insertion
            BigDecimal adminIdDecimal = new BigDecimal(admin.getAdminId());
            preparedStatement.setBigDecimal(5, adminIdDecimal);

            preparedStatement.executeUpdate();
            System.out.println("Admin inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void insertCoach(Coach coach) {
        String insertCoachSQL = "INSERT INTO coaches (id, specialty, bio, availability, courses) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Objects.requireNonNull(DAO.getConnection()).prepareStatement(insertCoachSQL)) {
            preparedStatement.setString(1, String.valueOf(coach.getId()));
            preparedStatement.setString(2, coach.getSpecialty());
            preparedStatement.setString(3, coach.getBio());
            preparedStatement.setString(4, coach.getAvailability());
            preparedStatement.setString(5, String.valueOf(coach.getCourses()));

            preparedStatement.executeUpdate();
            System.out.println("Coach inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.createUserTable();

        User user = new User(null, "hama", "hama", "hama.doe@example.com", "1990-01-01");
        userService.insertUser(user);

        Admin admin = new Admin(null, "Admin", "User", "admin@example.com", "1980-01-01", new BigInteger("12345"));
        userService.insertAdmin(admin);

        Coach coach = new Coach(null, "Coach", "yoga", "monday", "stretching" );
        userService.insertCoach(coach);
    }
}
