package org.example;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ActivityLogDAO dao = new ActivityLogDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Fitness Tracker ===");
            System.out.println("1. Add Activity");
            System.out.println("2. View Weekly Summary");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addActivity();
                    break;
                case 2:
                    viewSummary();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addActivity() {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter steps: ");
            int steps = scanner.nextInt();
            System.out.print("Enter calories burned: ");
            int calories = scanner.nextInt();
            System.out.print("Enter distance (km): ");
            float distance = scanner.nextFloat();

            var log = new ActivityLog(date, steps, calories, distance);
            dao.addActivity(log);
            System.out.println("Activity logged successfully!");
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void viewSummary() {
        try {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            List<ActivityLog> logs = dao.getActivityLogs(start, end);
            int totalSteps = 0, totalCalories = 0;
            float totalDistance = 0;

            System.out.println("\n=== Activity Summary ===");
            for (ActivityLog log : logs) {
                totalSteps += log.getSteps();
                totalCalories += log.getCalories();
                totalDistance += log.getDistance();
                System.out.printf("Date: %s | Steps: %d | Calories: %d | Distance: %.2f km%n",
                        log.getDate(), log.getSteps(), log.getCalories(), log.getDistance());
            }

            System.out.printf("\nTotal Steps: %d | Total Calories: %d | Total Distance: %.2f km%n",
                    totalSteps, totalCalories, totalDistance);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
