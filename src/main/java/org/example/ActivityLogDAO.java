package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
        import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/fitness_tracker";
    private final String jdbcUsername = "root"; // Change to your username
    private final String jdbcPassword = "ROOT"; // Change to your password

    public void addActivity(ActivityLog log) throws SQLException {
        String query = "INSERT INTO activity_logs (date, steps, calories, distance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(log.getDate()));
            stmt.setInt(2, log.getSteps());
            stmt.setInt(3, log.getCalories());
            stmt.setFloat(4, log.getDistance());
            stmt.executeUpdate();
        }
    }

    public List<ActivityLog> getActivityLogs(LocalDate start, LocalDate end) throws SQLException {
        String query = "SELECT * FROM activity_logs WHERE date BETWEEN ? AND ?";
        List<ActivityLog> logs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                int steps = rs.getInt("steps");
                int calories = rs.getInt("calories");
                float distance = rs.getFloat("distance");
                logs.add(new ActivityLog(date, steps, calories, distance));
            }
        }
        return logs;
    }
}
