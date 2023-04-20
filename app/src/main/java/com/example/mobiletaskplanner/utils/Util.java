package com.example.mobiletaskplanner.utils;

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static Map<TaskPriority, Integer> priorityIntegerMap= new HashMap<TaskPriority, Integer>() {{
        put(TaskPriority.LOW, 1);
        put(TaskPriority.MEDIUM, 2);
        put(TaskPriority.HIGH, 3);
    }};

    public static Map<Integer, TaskPriority> integerPriorityMap= new HashMap<Integer, TaskPriority>() {{
        put(1, TaskPriority.LOW);
        put(2, TaskPriority.MEDIUM);
        put(3, TaskPriority.HIGH);
    }};

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        } else {
            return username.length() >= 3;
        }
    }

    public static boolean isValidPassword(String password) {

        if (password == null) {
            return false;
        }
        //TODO debug
//        if (password.length() < 5) {
//            return false;
//        }
//        if (password.matches(".*[~#^|$%&*!].*")) {
//            return false;
//        }
//
//        if (!password.matches(".*[A-Z].*")) {
//            return false;
//        }
//
//        if (!password.matches(".*\\d.*")) {
//            return false;
//        }

        return true;
    }

    public static String getMonthName(int month, Context context) {
        switch (month) {
            case 1:
                return context.getString(R.string.january);
            case 2:
                return context.getString(R.string.february);
            case 3:
                return context.getString(R.string.march);
            case 4:
                return context.getString(R.string.april);
            case 5:
                return context.getString(R.string.may);
            case 6:
                return context.getString(R.string.june);
            case 7:
                return context.getString(R.string.july);
            case 8:
                return context.getString(R.string.august);
            case 9:
                return context.getString(R.string.september);
            case 10:
                return context.getString(R.string.october);
            case 11:
                return context.getString(R.string.november);
            case 12:
                return context.getString(R.string.december);
            default:
                return "";
        }
    }

    public static String formatTime(Task task) {
        return formatTimestamp(task.getStartTimeMinutes()) + " - " + formatTimestamp(task.getEndTimeMinutes());
    }

    private static String formatTimestamp(long unixTimestamp) {
        //convert seconds to milliseconds
        Date date = new Date(unixTimestamp * 1000L);
        //format of the date
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //format to a string
        return sdf.format(date);
    }

    public static String formatMonthDay(DateTasks dateTasks, Context context) {
        return Util.getMonthName(dateTasks.getMonth(), context) + " " + dateTasks.getDay() + ". " + dateTasks.getYear();
    }

    public static boolean isSameDay(Calendar calendar, DateTasks dateTasks) {
        return calendar.get(Calendar.YEAR) == dateTasks.getYear() &&
                calendar.get(Calendar.MONTH) + 1 == dateTasks.getMonth() &&
                calendar.get(Calendar.DAY_OF_MONTH) == dateTasks.getDay();
    }

    public static String formatTimeHourMinute(int h1, int m1, int h2, int m2) {
        String hour1 = h1 < 10 ? h1 == 0 ? "00"  : "0" + h1 : "" + h1;
        String minute1 = m1 < 10 ? m1 == 0 ? "00"  : "0" + m1 : "" + m1;
        String hour2 = h2 < 10 ? h2 == 0 ? "00"  : "0" + h2 : "" + h2;
        String minute2 = m2 < 10 ? m2 == 0 ? "00"  : "0" + m2 : "" + m2;
        return hour1 + ":" + minute1 + " - " + hour2 + ":" + minute2;
    }

    public static String formatTimeHourMinute(Task task) {
        return formatTimeHourMinute(task.getStartTimeMinutes()/60,
                task.getStartTimeMinutes()%60,
                task.getEndTimeMinutes()/60,
                task.getEndTimeMinutes()%60);

    }

}
