package com.example.mobiletaskplanner.models;

import com.example.mobiletaskplanner.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DateTasks implements Serializable {
    private int id;
    private int unixTime;
    private int day;
    private int month;
    private int year;
    private String date;
    private List<Task> tasks = new ArrayList<>();

    public DateTasks() { }

    public DateTasks(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public DateTasks(int id, String date, int day, int month, int year) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateTasks(String date, int day, int month, int year) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(int unixTime) {
        this.unixTime = unixTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
