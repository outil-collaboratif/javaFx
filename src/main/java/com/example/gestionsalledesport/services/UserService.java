package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Admin;
import com.example.gestionsalledesport.models.Coach;
import com.example.gestionsalledesport.models.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                "admin_id DECIMAL NOT NULL," + // Add a comma here
                "password VARCHAR(50)" + // Add the password column definition
                ")";

        dao.executeQuery(createAdminTableSQL);
        System.out.println("Admin table created successfully.");

        // Create 'coaches' table
        String createCoachTableSQL = "CREATE TABLE IF NOT EXISTS coaches (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(50) NOT NULL," +
                "prenom VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) UNIQUE NOT NULL," +
                "birthday VARCHAR(20) NOT NULL," +
                "specialite VARCHAR(50) NOT NULL" +
                ")";
        dao.executeQuery(createCoachTableSQL);
        System.out.println("Coach table created successfully.");
    }

    public void insertUser(User user) {
        String insertUserSQL = "INSERT INTO users (nom, prenom, email, birthday) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertUserSQL)) {
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectUsersSQL = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectUsersSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String birthday = resultSet.getString("birthday");

                User user = new User(id, nom, prenom, email, birthday);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void deleteUser(long userId) {
        // First, delete related abonnements
        String deleteAbonnementsSQL = "DELETE FROM abonnements WHERE user_id = ?";
    
        try (PreparedStatement abonnementsStatement = dao.getConnection().prepareStatement(deleteAbonnementsSQL)) {
            abonnementsStatement.setLong(1, userId);
            abonnementsStatement.executeUpdate();
            System.out.println("Abonnements of the user deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete abonnements of the user", e);
        }
    
        // After deleting related abonnements, delete the user
        String deleteUserSQL = "DELETE FROM users WHERE id = ?";
    
        try (PreparedStatement userStatement = dao.getConnection().prepareStatement(deleteUserSQL)) {
            userStatement.setLong(1, userId);
            userStatement.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete the user", e);
        }
    }
    

    public void updateUser(User user) {
        String updateUserSQL = "UPDATE users SET nom = ?, prenom = ?, email = ?, birthday = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(updateUserSQL)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getBirthday());
            preparedStatement.setLong(5, user.getId());

            preparedStatement.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAdmin(Admin admin) {
        String insertAdminSQL = "INSERT INTO admins (nom, prenom, email, birthday, admin_id,password) VALUES (?, ?, ?, ?,?,?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertAdminSQL)) {
            preparedStatement.setString(1, admin.getNom());
            preparedStatement.setString(2, admin.getPrenom());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getBirthday());
            preparedStatement.setString(5, admin.getPassword());

            // Convert BigInteger to BigDecimal for insertion
            BigDecimal adminIdDecimal = new BigDecimal(admin.getAdminId());
            preparedStatement.setBigDecimal(6, adminIdDecimal);

            preparedStatement.executeUpdate();
            System.out.println("Admin inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertCoach(Coach coach) {
        String insertCoachSQL = "INSERT INTO coaches (nom, prenom, email, birthday, specialite) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertCoachSQL)) {
            preparedStatement.setString(1, coach.getNom());
            preparedStatement.setString(2, coach.getPrenom());
            preparedStatement.setString(3, coach.getEmail());
            preparedStatement.setString(4, coach.getBirthday());
            preparedStatement.setString(5, coach.getSpecialite());

            preparedStatement.executeUpdate();
            System.out.println("Coach inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.createUserTable();

        User user = new User(null, "baraket", "chaima", "chaima@gmail.com", "1999-03-08");
        userService.insertUser(user);

        Admin admin = new Admin(null, "Admin", "User", "admin@example.com", "1980-01-01", new BigInteger("12345"),
                "123");
        userService.insertAdmin(admin);
        Admin admin2 = new Admin(null, "Baraket", "Chaima", "chaima@gmail.com", "1999-03-08", new BigInteger("12345"),
                "123");
        userService.insertAdmin(admin2);

        Coach coach = new Coach(null, "Coach", "Trainer", "coach@example.com", "1975-01-01", "Fitness");
        userService.insertCoach(coach);
    }
}
