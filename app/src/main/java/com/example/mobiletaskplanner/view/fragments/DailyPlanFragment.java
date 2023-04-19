package com.example.mobiletaskplanner.view.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.activities.TaskPage;
import com.example.mobiletaskplanner.view.recycler.adapter.DateAdapter;
import com.example.mobiletaskplanner.view.recycler.adapter.TaskAdapter;
import com.example.mobiletaskplanner.view.recycler.differ.DateTasksDiffItemCallback;
import com.example.mobiletaskplanner.view.recycler.differ.TaskDiffItemCallback;
import com.example.mobiletaskplanner.view.viewmodels.CalendarRecyclerViewModel;
import com.example.mobiletaskplanner.view.viewmodels.DailyTasksRecyclerViewModel;
import com.example.mobiletaskplanner.view.viewmodels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

public class DailyPlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private DailyTasksRecyclerViewModel recyclerViewModel;
    private TextView currentDateTv;
    private SharedViewModel sharedViewModel;
    private FloatingActionButton addTaskBtn;
    private ActivityResultLauncher<Intent> taskActivityResultLauncher;

    private TextView taskPriorityLow;
    private TextView taskPriorityMedium;
    private TextView taskPriorityHigh;
    private int priorityCounter = 3;

    private TaskAdapter taskAdapter;

    public DailyPlanFragment() {
        super(R.layout.fragment_dailyplan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dailyplan, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewModel = new ViewModelProvider(this).get(DailyTasksRecyclerViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init(view);

    }

    private void init(View view) {
        initView(view);
        initObservers(view);
        initRecycler();
        initListeners();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.taskListRv);
        currentDateTv = view.findViewById(R.id.currentDateTv);
        addTaskBtn = view.findViewById(R.id.add_new_task_btn);

        taskPriorityLow = view.findViewById(R.id.plan_task_priority_low);
        taskPriorityMedium = view.findViewById(R.id.plan_task_priority_medium);
        taskPriorityHigh = view.findViewById(R.id.plan_task_priority_high);
    }

    private void initObservers(View view) {
        recyclerViewModel.getMutableLiveDataTasks().observe( getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitList(tasks);
        });
        sharedViewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> {
//            currentDateTv.setText(date);
            Log.e("Timber", "primio");
            recyclerViewModel.changeDateTasks(date);
            taskAdapter.notifyDataSetChanged();
            currentDateTv.setText(Util.formatMonthDay(date, getContext()));
        });
    }

    private void initRecycler() {
        taskAdapter = new TaskAdapter(new TaskDiffItemCallback(), task -> {
            Intent intent = new Intent(getContext(), TaskPage.class);
            intent.putExtra(Constants.TASK_ACTION_TYPE, Constants.TASK_ACTION_TYPE_VIEW);
            intent.putExtra(Constants.TASK_DATA, task);
            taskActivityResultLauncher.launch(intent);
        }, new TaskAdapter.OnItemClickListener() {

            @Override
            public void onEdit(Task task) {
                Toast.makeText(getContext(), task.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TaskPage.class);
                intent.putExtra(Constants.TASK_ACTION_TYPE, Constants.TASK_ACTION_TYPE_EDIT);
                intent.putExtra(Constants.TASK_DATA, task);
                taskActivityResultLauncher.launch(intent);
            }

            @Override
            public void onDelete(Task task) {
                recyclerViewModel.deleteTask(task.getId());
                refreshView();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);

    }

    private void initListeners() {

        taskActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Constants.RESULT_OK) {
                            Intent data = result.getData();
                            String taskCode = data.getStringExtra(Constants.TASK_CODE);
                            if(taskCode.equals(Constants.TASK_CODE_NOTHING)) {
                                return;
                            }
                            Task task = (Task) data.getSerializableExtra(Constants.TASK_DATA);
                            if(taskCode.equals(Constants.TASK_CODE_ADD)) {
                                recyclerViewModel.addTask(task);
                                refreshView();
                            }
                            else if(taskCode.equals(Constants.TASK_CODE_EDIT)) {
                                recyclerViewModel.editTask(task);
                                refreshView();
                            }
                            else if(taskCode.equals(Constants.TASK_CODE_REMOVE)) {
                                recyclerViewModel.deleteTask(task.getId());
                                refreshView();
                            }
                        }
                    }
                });

        addTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaskPage.class);
            intent.putExtra(Constants.TASK_ACTION_TYPE, Constants.TASK_ACTION_TYPE_ADD);
            taskActivityResultLauncher.launch(intent);
        });

        taskPriorityLow.setOnClickListener(v -> {
            boolean newState = changePriorityView(taskPriorityLow, R.color.task_priority_low, R.color.task_priority_low_disabled);
            recyclerViewModel.changePriorityState(TaskPriority.LOW, newState);
            
        });

        taskPriorityMedium.setOnClickListener(v -> {
            boolean newState = changePriorityView(taskPriorityMedium, R.color.task_priority_medium, R.color.task_priority_medium_disabled);
            recyclerViewModel.changePriorityState(TaskPriority.MEDIUM, newState);
        });

        taskPriorityHigh.setOnClickListener(v -> {
            boolean newState = changePriorityView(taskPriorityHigh, R.color.task_priority_high, R.color.task_priority_high_disabled);
            recyclerViewModel.changePriorityState(TaskPriority.HIGH, newState);
        });
    }


    // returns a new state
    private boolean changePriorityView(TextView tv, int activeColor, int disabledColor) {
        int color = ((ColorDrawable) tv.getBackground().getConstantState().newDrawable()).getColor();

        if(color == ContextCompat.getColor(getContext(), disabledColor)) {
            tv.setBackgroundColor(ContextCompat.getColor(getContext(), activeColor));
            priorityCounter++;
            return true;
        }
        if (priorityCounter == 1) {
            return true;
        }
        tv.setBackgroundColor(ContextCompat.getColor(getContext(), disabledColor));
        priorityCounter--;
        return false;
    }

    private void refreshView() {
        taskAdapter.notifyDataSetChanged();
        sharedViewModel.storeRefreshView(true);
    }


}