package com.example.mobiletaskplanner.utils;

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;

import java.util.Date;

public class Util {
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
        return formatTimestamp(task.getStartTime()) + " - " + formatTimestamp(task.getEndTime());
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

}
