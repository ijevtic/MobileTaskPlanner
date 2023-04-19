package com.example.mobiletaskplanner.models;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;
    private String title;
    private int startTimeMinutes;
    private int endTimeMinutes;
    private String description;
    private TaskPriority priority;

    public Task() { }

    public void copyTask(Task t) {
        this.id = t.getId();
        this.title = t.getTitle();
        this.startTimeMinutes = t.startTimeMinutes;
        this.endTimeMinutes = t.endTimeMinutes;
        this.description = t.getDescription();
        this.priority = t.getPriority();
    }

    public Task(String title, int startTime, int endTime, String description, TaskPriority priority) {
        this.title = title;
        this.startTimeMinutes = startTime;
        this.endTimeMinutes = endTime;
        this.description = description;
        this.priority = priority;
    }

    public Task(int id, String title, int startTime, int endTime, String description, TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.startTimeMinutes = startTime;
        this.endTimeMinutes = endTime;
        this.description = description;
        this.priority = priority;
    }

    public Task(String title, int startTime, int endTime) {
        this.title = title;
        this.startTimeMinutes = startTime;
        this.endTimeMinutes = endTime;
        this.description = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartTimeMinutes() {
        return startTimeMinutes;
    }

    public void setStartTimeMinutes(int startTimeMinutes) {
        this.startTimeMinutes = startTimeMinutes;
    }

    public int getEndTimeMinutes() {
        return endTimeMinutes;
    }

    public void setEndTimeMinutes(int endTimeMinutes) {
        this.endTimeMinutes = endTimeMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
