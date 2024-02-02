package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Admin;
import com.example.gestionsalledesport.models.Coach;
import com.example.gestionsalledesport.models.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                "password VARCHAR(50) NOT NULL," +
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

    public void insertAdmin(Admin admin) {
        String insertAdminSQL = "INSERT INTO admins (nom, prenom, email, birthday, admin_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertAdminSQL)) {
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
    public boolean login(String email, String password) {
        String selectUserSQL = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectUserSQL)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("password");
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        System.out.println("Login successful. Welcome, " + email + "!");
                        return true;
                    } else {
                        System.out.println("Invalid password. Please try again.");
                    }
                } else {
                    System.out.println("User not found. Please check your email.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.createUserTable();

        User user = new User(null, "username", "userlastname", "user@example.com", "userpassword","1990-01-01");
        userService.insertUser(user);

        Admin admin = new Admin(null, "Admin", "User", "admin@example.com", "1980-01-01", new BigInteger("12345"));
        userService.insertAdmin(admin);

        Coach coach = new Coach(null, "Coach", "Trainer", "coach@example.com", "1975-01-01", "Fitness");
        userService.insertCoach(coach);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String userEmail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String userPassword = scanner.nextLine();
        userService.login(userEmail, userPassword);
    }
}
