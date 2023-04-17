package com.example.mobiletaskplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.utils.Constants;

public class ManageTaskFragment extends Fragment {

    private EditText taskTitle;
    private EditText taskTime;
    private EditText taskDescription;
    private Button cancelBtn;
    private Button saveBtn;
    private Button createBtn;

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
    }

    private void initListeners() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTaskAndFinish(Constants.TASK_CODE_EDIT);
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check overlapping, empty fields
                submitTaskAndFinish(Constants.TASK_CODE_ADD);
            }
        });

    }

    private void setupView() {

        Bundle args = getArguments();
        if (args != null) {
            String value = args.getString(Constants.TASK_ACTION_TYPE);
            if(value.equals(Constants.TASK_ACTION_TYPE_EDIT)) {
                Task task = (Task) args.getSerializable(Constants.TASK_DATA);
                saveBtn.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.GONE);
                taskTitle.setText(task.getTitle());
//                taskTime.setText(String.valueOf(task.getTime()));
                taskDescription.setText(task.getDescription());
            }
            else {
                saveBtn.setVisibility(View.GONE);
                createBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    private void submitTaskAndFinish(String taskCode) {
        Task task = getTaskFromInput();
        Intent intent = new Intent();
        intent.putExtra(Constants.TASK_CODE, taskCode);
        intent.putExtra(Constants.TASK_DATA, task);
        getActivity().setResult(Constants.RESULT_OK, intent);
        getActivity().finish();
    }

    private Task getTaskFromInput() {
        Task task = new Task();
        task.setTitle(taskTitle.getText().toString());
//        task.setTime(taskTime.getText().toString().);
        task.setDescription(taskDescription.getText().toString());
        return task;
    }
}
