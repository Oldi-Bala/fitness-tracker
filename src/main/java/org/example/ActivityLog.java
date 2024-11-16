package org.example;

import java.time.LocalDate;

public class ActivityLog {
    private LocalDate date;
    private int steps;
    private int calories;
    private float distance;

    public ActivityLog(LocalDate date, int steps, int calories, float distance) {
        this.date = date;
        this.steps = steps;
        this.calories = calories;
        this.distance = distance;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public int getSteps() { return steps; }
    public int getCalories() { return calories; }
    public float getDistance() { return distance; }
}
