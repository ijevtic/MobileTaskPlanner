package com.example.mobiletaskplanner.models;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;
    private String title;
    private int startTime;
    private int endTime;
    private String description;

    public Task() { }

    public Task(String title, int startTime, int endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public Task(String title, int startTime, int endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
