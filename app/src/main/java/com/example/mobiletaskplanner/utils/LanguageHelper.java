package com.example.mobiletaskplanner.utils;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {
    public static void updateLanguage(Resources resources) {
        String language = Locale.getDefault().getLanguage();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language));
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
