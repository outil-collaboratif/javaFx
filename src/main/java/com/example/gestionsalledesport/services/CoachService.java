package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Coach;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachService {
    private final DAO dao;

    public CoachService() {
        this.dao = new DAO();
    }

    public void createCoachTable() {
        String createCoachTableSQL = "CREATE TABLE IF NOT EXISTS coaches (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "specialty VARCHAR(100) NOT NULL," +
                "bio TEXT," +
                "availability VARCHAR(100)," +
                "courseId INT," +
                "FOREIGN KEY (courseId) REFERENCES courses(id)" +
                ")";
        dao.executeQuery(createCoachTableSQL);
        System.out.println("Coach table created successfully.");
    }

    public void insertEntity(Coach coach) {
        String insertCoachSQL = "INSERT INTO coaches (specialty, bio, availability, courseId) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(insertCoachSQL)) {
            preparedStatement.setString(1, coach.getSpecialty());
            preparedStatement.setString(2, coach.getBio());
            preparedStatement.setString(3, coach.getAvailability());
            String coursesString = String.join(" ", coach.getCourses());
            preparedStatement.setString(4, coursesString);
            preparedStatement.executeUpdate();
            System.out.println("Coach inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEntity(Coach coach) {
        String updateCoachSQL = "UPDATE coaches SET specialty=?, bio=?, availability=?, courseId=? WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(updateCoachSQL)) {
            preparedStatement.setString(1, coach.getSpecialty());
            preparedStatement.setString(2, coach.getBio());
            preparedStatement.setString(3, coach.getAvailability());
            String coursesString = String.join(" ", coach.getCourses());
            preparedStatement.setString(4, coursesString);
            preparedStatement.setLong(5, coach.getId());
            preparedStatement.executeUpdate();
            System.out.println("Coach updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntity(long coachId) {
        String deleteCoachSQL = "DELETE FROM coaches WHERE id=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(deleteCoachSQL)) {
            preparedStatement.setLong(1, coachId);

            preparedStatement.executeUpdate();
            System.out.println("Coach deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getEntityDates(long coachId) {
        List<String> coursesDates = new ArrayList<>();
        String selectCoursesDatesSQL = "SELECT date FROM courses WHERE coachId=?";

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectCoursesDatesSQL)) {
            preparedStatement.setLong(1, coachId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                coursesDates.add(date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return coursesDates;
    }


}
