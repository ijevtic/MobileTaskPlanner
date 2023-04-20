package com.example.mobiletaskplanner.view.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.view.fragments.ViewTaskFragment;
import com.example.mobiletaskplanner.view.viewmodels.PagerFragmentViewModel;

public class ScreenSlidePagerActivity extends Fragment {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    private DateTasks dateTasks;

    private int currentTask;

    PagerFragmentViewModel viewModel;

    public ScreenSlidePagerActivity() {
        super(R.layout.activity_screen_slide);
    }

    public ScreenSlidePagerActivity(DateTasks dateTasks, int currentTask) {
        super(R.layout.activity_screen_slide);
        this.dateTasks = dateTasks;
        this.currentTask = currentTask;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(PagerFragmentViewModel.class);
        viewModel.setDateTasks(dateTasks);
//        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager2 and a PagerAdapter.
//        viewPager = findViewById(R.id.pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(this);
//        viewPager.setAdapter(pagerAdapter);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.activity_screen_slide, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        init();
    }

    private void init() {
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity());
        initView();

    }

    private void initView() {
        viewPager = getView().findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentTask);
    }

//    @Override
//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new ViewTaskFragment(position);
        }

        @Override
        public int getItemCount() {
            return viewModel.getDateTasks().getValue().getTasks().size();
        }
    }
}
