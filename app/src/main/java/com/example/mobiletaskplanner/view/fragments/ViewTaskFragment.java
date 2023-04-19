package com.example.mobiletaskplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.viewmodels.EditTaskViewModel;
import com.example.mobiletaskplanner.view.viewmodels.SharedViewModel;

public class ViewTaskFragment extends Fragment {

    private TextView taskDate;
    private TextView taskTitle;
    private TextView taskTime;
    private TextView taskDescription;
    private Button editBtn;
    private Button deleteBtn;

    private Task task;
    private DateTasks dateTasks;
    private EditTaskViewModel editTaskViewModel;



    public ViewTaskFragment() {
        super(R.layout.fragment_view_task);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTaskViewModel = new ViewModelProvider(requireActivity()).get(EditTaskViewModel.class);
        init();
    }

    private void init() {
        initView();
        initListeners();
        setupView();
    }

    private void initView() {
        taskDate = getView().findViewById(R.id.vt_task_date);
        taskTitle = getView().findViewById(R.id.vt_task_title);
        taskTime = getView().findViewById(R.id.vt_task_time);
        taskDescription = getView().findViewById(R.id.vt_task_desc);
        editBtn = getView().findViewById(R.id.vt_edit_btn);
        deleteBtn = getView().findViewById(R.id.vt_delete_btn);
    }

    private void initListeners() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskViewModel.storeTask(task);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO make popup to confirm delete
                Intent intent = new Intent();
                intent.putExtra(Constants.TASK_CODE, Constants.TASK_CODE_REMOVE);
                intent.putExtra(Constants.TASK_DATA, task);
                getActivity().setResult(Constants.RESULT_OK, intent);
                getActivity().finish();
            }
        });

    }

    private void setupView() {

        Bundle args = getArguments();
        if (args != null) {
            dateTasks = (DateTasks) args.getSerializable(Constants.DATE_DATA);
            task = (Task) args.getSerializable(Constants.TASK_DATA);
            taskDate.setText(Util.formatMonthDay(dateTasks, getContext()));
            taskTitle.setText(task.getTitle());
            taskTime.setText(Util.formatTimeHourMinute(task));
            taskDescription.setText(task.getDescription());
        }
    }
}