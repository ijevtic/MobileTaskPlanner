package com.example.mobiletaskplanner.view.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobiletaskplanner.view.fragments.CalendarFragment;
import com.example.mobiletaskplanner.view.fragments.DailyPlanFragment;
import com.example.mobiletaskplanner.view.fragments.ProfileFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_CALENDAR = 0;
    public static final int FRAGMENT_DAILYPLAN = 1;
    public static final int FRAGMENT_PROFILE = 2;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_CALENDAR: fragment = new CalendarFragment(); break;
            case FRAGMENT_DAILYPLAN: fragment = new DailyPlanFragment(); break;
            default: fragment = new ProfileFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_CALENDAR: return "1";
            case FRAGMENT_DAILYPLAN: return "2";
            default: return "3";
        }
    }
}
