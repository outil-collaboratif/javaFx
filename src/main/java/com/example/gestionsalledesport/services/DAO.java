package com.example.gestionsalledesport.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sport";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return conn;
        } catch (Exception ex) {
            System.out.println("Error establishing the database connection: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            if (conn != null) {
                st = conn.createStatement();
                st.executeUpdate(query);
                System.out.println("Query executed successfully.");
            } else {
                System.out.println("Connection is null. Cannot execute query.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}