package com.example.mobiletaskplanner.models;

public class DateTasks {
    private int id;
    private String date;

    public DateTasks(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
