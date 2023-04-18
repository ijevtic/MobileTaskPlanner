package com.example.mobiletaskplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.view.viewmodels.SharedViewModel;
import com.example.mobiletaskplanner.view.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    private void initListeners() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_CALENDAR, true); break;
                case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_DAILYPLAN, true); break;
                case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_PROFILE, true); break;
            }
            return true;
        });

        sharedViewModel.getSelectedDate().observe(this, date -> {
            viewPager.setCurrentItem(PagerAdapter.FRAGMENT_DAILYPLAN, true);

            // Get the ID of the menu item to select
            int itemId = R.id.navigation_2;

            // Set the selected item in the BottomNavigationView
            bottomNavigationView.setSelectedItemId(itemId);
        });

    }


}