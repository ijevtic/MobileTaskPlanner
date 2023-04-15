package com.example.mobiletaskplanner.application;

import android.app.Application;
import timber.log.Timber;

public class MobileTaskPlannerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
