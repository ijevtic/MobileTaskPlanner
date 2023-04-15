package com.example.mobiletaskplanner.utils;

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

}
