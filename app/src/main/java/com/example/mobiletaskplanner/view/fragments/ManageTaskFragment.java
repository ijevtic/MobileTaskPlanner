package com.example.mobiletaskplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;
import com.example.mobiletaskplanner.utils.Constants;

public class ManageTaskFragment extends Fragment {

    private EditText taskTitle;
    private EditText taskTime;
    private EditText taskDescription;

    private Button cancelBtn;
    private Button saveBtn;
    private Button createBtn;

    private TextView lowPriorityTv;
    private TextView mediumPriorityTv;
    private TextView highPriorityTv;

    private Task task;
    private TaskPriority taskPriority;

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
    }

    private void setupView() {

        Bundle args = getArguments();
        if (args != null) {
            String value = args.getString(Constants.TASK_ACTION_TYPE);
            if(value.equals(Constants.TASK_ACTION_TYPE_EDIT)) {
                task = (Task) args.getSerializable(Constants.TASK_DATA);
                saveBtn.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.GONE);
                taskTitle.setText(task.getTitle());
//                taskTime.setText(String.valueOf(task.getTime()));
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
        updateTaskFromImput();
        Intent intent = new Intent();
        intent.putExtra(Constants.TASK_CODE, taskCode);
        intent.putExtra(Constants.TASK_DATA, task);
        getActivity().setResult(Constants.RESULT_OK, intent);
        getActivity().finish();
    }

    private Task updateTaskFromImput() {
        task.setTitle(taskTitle.getText().toString());
//        task.setTime(taskTime.getText().toString().);
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
}
