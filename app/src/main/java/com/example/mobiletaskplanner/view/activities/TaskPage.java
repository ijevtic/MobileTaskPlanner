package com.example.mobiletaskplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.view.fragments.ManageTaskFragment;
import com.example.mobiletaskplanner.view.fragments.ViewTaskFragment;

public class TaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);
        init();
    }

    private void init() {
        initFragment();
        initListeners();
    }

    private void initFragment() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String taskActionType = extras.getString(Constants.TASK_ACTION_TYPE);
            Fragment fragment = null;
            if(taskActionType.equals(Constants.TASK_ACTION_TYPE_VIEW)) {
                fragment = new ViewTaskFragment();
            }
            else {
                fragment = new ManageTaskFragment();
                Bundle bundle = new Bundle();

                if(taskActionType.equals(Constants.TASK_ACTION_TYPE_EDIT)) { //edit
                    Task task = (Task) extras.getSerializable(Constants.TASK_CODE);
                    bundle.putSerializable(Constants.TASK_CODE, task);
                }
                // else add

                bundle.putString(Constants.TASK_ACTION_TYPE, taskActionType);

                fragment.setArguments(bundle);
            }
            transaction.add(R.id.taskFragment, fragment);
            transaction.commit();
        }

    }

    private void initListeners() {

    }
}