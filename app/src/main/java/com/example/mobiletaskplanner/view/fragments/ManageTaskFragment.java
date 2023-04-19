package com.example.mobiletaskplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;
import com.example.mobiletaskplanner.models.TimeType;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;

public class ManageTaskFragment extends Fragment {

    private EditText taskTitle;
    private TextView taskTime;
    private EditText taskDescription;

    private Button cancelBtn;
    private Button saveBtn;
    private Button createBtn;

    private TextView lowPriorityTv;
    private TextView mediumPriorityTv;
    private TextView highPriorityTv;

    private Task task;
    private int startTimeHour = -1;
    private int startTimeMinute = -1;
    private int endTimeHour = -1;
    private int endTimeMinute = -1;
    private TaskPriority taskPriority = TaskPriority.NONE;

    public ManageTaskFragment() {
        super(R.layout.fragment_manage_task);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initView();
        initListeners();
        setupView();
    }

    private void initView() {
        taskTitle = getView().findViewById(R.id.mt_task_title);
        taskTime = getView().findViewById(R.id.mt_task_time);
        taskDescription = getView().findViewById(R.id.mt_task_desc);

        saveBtn = getView().findViewById(R.id.saveTaskButton);
        createBtn = getView().findViewById(R.id.createTaskButton);
        cancelBtn = getView().findViewById(R.id.cancelTaskButton);

        lowPriorityTv = getView().findViewById(R.id.mt_task_priority_low);
        mediumPriorityTv = getView().findViewById(R.id.mt_task_priority_medium);
        highPriorityTv = getView().findViewById(R.id.mt_task_priority_high);

        Log.e("Timer", "2");


    }


    private void initListeners() {



        cancelBtn.setOnClickListener(v -> {
            getActivity().finish();
        });
        saveBtn.setOnClickListener(v -> {
            submitTaskAndFinish(Constants.TASK_CODE_EDIT);
        });

        createBtn.setOnClickListener(v -> {
            submitTaskAndFinish(Constants.TASK_CODE_ADD);
        });

        lowPriorityTv.setOnClickListener(v -> {
            setupPriority(TaskPriority.LOW);
        });

        mediumPriorityTv.setOnClickListener(v -> {
            setupPriority(TaskPriority.MEDIUM);
        });

        highPriorityTv.setOnClickListener(v -> {
            setupPriority(TaskPriority.HIGH);
        });

        taskTime.setOnClickListener(v -> {
            openTimeDialog(TimeType.START_TIME);
        });

        getParentFragmentManager().setFragmentResultListener(Constants.TIME_REQUEST_KEY, this, (requestKey, result) -> {
            TimeType timeType = (TimeType) result.getSerializable(Constants.TIME_TYPE);
            if(timeType == null)
                return;
            int hour = result.getInt(Constants.TIME_RESULT_HOUR);
            int minute = result.getInt(Constants.TIME_RESULT_MINUTE);
            if(timeType.equals(TimeType.START_TIME)) {
                startTimeHour = hour;
                startTimeMinute = minute;
                openTimeDialog(TimeType.END_TIME);
            }
            else {
                endTimeHour = hour;
                endTimeMinute = minute;
                if(startTimeHour*60 + startTimeMinute >= endTimeHour*60 + endTimeMinute) {
                    Toast.makeText(requireContext(), "End time must be after start time", Toast.LENGTH_SHORT).show();
                    taskTime.setText("");
                    return;
                }
                taskTime.setText(Util.formatTimeHourMinute(startTimeHour, startTimeMinute, endTimeHour, endTimeMinute));
            }
        });


    }

    private void setupView() {

        Bundle args = getArguments();
        if (args != null) {
            String value = args.getString(Constants.TASK_ACTION_TYPE);
            if(value.equals(Constants.TASK_ACTION_TYPE_EDIT)) {
                task = (Task) args.getSerializable(Constants.TASK_DATA);
                saveBtn.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.GONE);
                
                startTimeHour = task.getStartTimeMinutes()/60;
                startTimeMinute = task.getStartTimeMinutes()%60;
                endTimeHour = task.getEndTimeMinutes()/60;
                endTimeMinute = task.getEndTimeMinutes()%60;

                taskTitle.setText(task.getTitle());
                taskTime.setText(Util.formatTimeHourMinute(task));
                taskDescription.setText(task.getDescription());

                setupPriority(task.getPriority());
            }
            else {
                this.task = new Task();
                setupPriority(TaskPriority.NONE);
                saveBtn.setVisibility(View.GONE);
                createBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    private void submitTaskAndFinish(String taskCode) {
        if(!isTaskFilled())
            return;
        updateTaskFromImput();
        Intent intent = new Intent();
        intent.putExtra(Constants.TASK_CODE, taskCode);
        intent.putExtra(Constants.TASK_DATA, task);
        getActivity().setResult(Constants.RESULT_OK, intent);
        getActivity().finish();
    }

    private boolean isTaskFilled() {
        if(taskTitle.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(taskTime.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Time is required", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(taskDescription.getText().toString().isEmpty()) {
//            Toast.makeText(requireContext(), "Description is required", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if(taskPriority.equals(TaskPriority.NONE)) {
            Toast.makeText(requireContext(), "Priority is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(startTimeHour == -1 || startTimeMinute == -1 || endTimeHour == -1 || endTimeMinute == -1) {
            Toast.makeText(requireContext(), "Time is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Task updateTaskFromImput() {
        task.setTitle(taskTitle.getText().toString());
        task.setStartTimeMinutes(startTimeHour*60 + startTimeMinute);
        task.setEndTimeMinutes(endTimeHour*60 + endTimeMinute);
        task.setDescription(taskDescription.getText().toString());
        task.setPriority(taskPriority);
        return task;
    }

    private void setupPriority(TaskPriority priority) {
        //ovo mora da stoji
        taskPriority = priority;

        int colorLow = ContextCompat.getColor(requireContext(), R.color.task_priority_low_disabled);
        int colorMedium = ContextCompat.getColor(requireContext(), R.color.task_priority_medium_disabled);
        int colorHigh = ContextCompat.getColor(requireContext(), R.color.task_priority_high_disabled);
        if(priority.equals(TaskPriority.LOW)) {
            colorLow = ContextCompat.getColor(requireContext(), R.color.task_priority_low);
        }
        else if(priority.equals(TaskPriority.MEDIUM)) {
            colorMedium = ContextCompat.getColor(requireContext(), R.color.task_priority_medium);
        }
        else if(priority.equals(TaskPriority.HIGH)) {
            colorHigh = ContextCompat.getColor(requireContext(), R.color.task_priority_high);
        }
        lowPriorityTv.setBackgroundColor(colorLow);
        mediumPriorityTv.setBackgroundColor(colorMedium);
        highPriorityTv.setBackgroundColor(colorHigh);
    }

    private void openTimeDialog(TimeType timeType) {
        TimePickerFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TIME_TYPE, timeType);
        newFragment.setArguments(args);
        newFragment.show(getParentFragmentManager(), "timePicker");
    }
}
