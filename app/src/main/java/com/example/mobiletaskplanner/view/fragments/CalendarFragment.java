package com.example.mobiletaskplanner.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.recycler.adapter.DateAdapter;
import com.example.mobiletaskplanner.view.recycler.differ.DateTasksDiffItemCallback;
import com.example.mobiletaskplanner.view.viewmodels.CalendarRecyclerViewModel;
import com.example.mobiletaskplanner.view.viewmodels.SharedViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import timber.log.Timber;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private CalendarRecyclerViewModel recyclerViewModel;
    private SharedViewModel sharedViewModel;
    private TextView monthYearText;

    private DateAdapter dateAdapter;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewModel = new ViewModelProvider(this).get(CalendarRecyclerViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init(view);

    }
    public static List<DateTasks> printDatesInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        List<DateTasks> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(new DateTasks(String.valueOf(cal.getTime()),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH)+1,
                    cal.get(Calendar.YEAR)));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    private void init(View view) {
        initView(view);
        initObservers(view);
        initRecycler();

        List<DateTasks> dates = printDatesInMonth(2018, 1);
        for(DateTasks date: dates) {
            recyclerViewModel.addDate(date);
        }

        recyclerView.scrollToPosition(1931);

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.listRv);
        monthYearText = view.findViewById(R.id.monthYearText);
    }

    private void initObservers(View view) {
        recyclerViewModel.getMutableLiveDataDates().observe( getViewLifecycleOwner(), dates -> {
            dateAdapter.submitList(dates);
        });
    }

    private void initRecycler() {
        dateAdapter = new DateAdapter(new DateTasksDiffItemCallback(), date -> {
            Log.e("Timber", "poslao");
            sharedViewModel.storeSelectedDate(date);
//            Toast.makeText(getContext(), date.getDay()+" "+date.getMonth(), Toast.LENGTH_SHORT).show();
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dateAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                try {
                    int firstVisibleItemIndex = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    firstVisibleItemIndex = Math.min(firstVisibleItemIndex+7, recyclerViewModel.getDateCount()-1);
                    DateTasks date = recyclerViewModel.getDateTasks(firstVisibleItemIndex);
                    String newMonthYearLabel = Util.getMonthName(
                            date.getMonth(), recyclerView.getContext())  + " " + date.getYear();
                    monthYearText.setText(newMonthYearLabel);
                }
                catch (Exception e) {}

            }
        });

    }
}