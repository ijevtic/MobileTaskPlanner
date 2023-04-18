package com.example.mobiletaskplanner.models;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;
    private String title;
    private int startTime;
    private int endTime;
    private String description;
    private TaskPriority priority;

    public Task() { }

    public void copyTask(Task t) {
        this.id = t.getId();
        this.title = t.getTitle();
        this.startTime = t.getStartTime();
        this.endTime = t.getEndTime();
        this.description = t.getDescription();
        this.priority = t.getPriority();
    }

    public Task(String title, int startTime, int endTime, String description, TaskPriority priority) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.priority = priority;
    }

    public Task(int id, String title, int startTime, int endTime, String description, TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.priority = priority;
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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
