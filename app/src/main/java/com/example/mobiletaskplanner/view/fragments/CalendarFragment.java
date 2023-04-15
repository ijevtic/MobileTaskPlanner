package com.example.mobiletaskplanner.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.view.recycler.adapter.DateAdapter;
import com.example.mobiletaskplanner.view.recycler.differ.DateTasksDiffItemCallback;
import com.example.mobiletaskplanner.view.viewmodels.RecyclerViewModel;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewModel recyclerViewModel;

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
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init(view);

    }

    private void init(View view) {
        initView(view);
        initObservers(view);
        initRecycler();

        for(int i = 0; i < 600; i++) {
            recyclerViewModel.addDateTasks();
        }

        recyclerView.scrollToPosition(50);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.listRv);
    }

    private void initObservers(View view) {
        recyclerViewModel.getMutableLiveDataDates().observe( getViewLifecycleOwner(), dates -> {
            dateAdapter.submitList(dates);
        });
    }

    private void initRecycler() {
        dateAdapter = new DateAdapter(new DateTasksDiffItemCallback(), date -> {
            Toast.makeText(getContext(), date.getDate(), Toast.LENGTH_SHORT).show();
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
//        gridLayoutManager.generateLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dateAdapter);

    }



//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
}