package com.example.gestionsalledesport.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.gestionsalledesport.models.Admin;
import com.example.gestionsalledesport.models.User;

public class LoginService {
    private DAO dao;

    public LoginService() {
        dao = new DAO();
    }

    // Validate credentials using database
    public Admin getUserByEmail(String email) {
        Connection conn = dao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Admin admin = null;
        try {
            String query = "SELECT * FROM admins WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(
                    rs.getLong("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("birthday"),
                    null, rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admin;
    }

    // Validate user credentials
    public boolean isValidCredentials(String email, String password) {
        Admin admin = getUserByEmail(email);
        return admin != null && admin.getPassword().equals(password);
    }
}
