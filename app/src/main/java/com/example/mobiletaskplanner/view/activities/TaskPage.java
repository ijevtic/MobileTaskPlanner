package com.example.mobiletaskplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.LanguageHelper;
import com.example.mobiletaskplanner.view.fragments.ManageTaskFragment;
import com.example.mobiletaskplanner.view.viewmodels.EditTaskViewModel;
import com.example.mobiletaskplanner.view.viewmodels.TasksViewModel;

public class TaskPage extends AppCompatActivity {

    private EditTaskViewModel editTaskViewModel;
    private TasksViewModel tasksViewModel;
    private ScreenSlidePagerActivity screenSlidePagerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.updateLanguage(getResources());
        setContentView(R.layout.activity_task_page);
        editTaskViewModel = new ViewModelProvider(this).get(EditTaskViewModel.class);
        tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
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
            Bundle bundle = new Bundle();

            DateTasks dateTasks = (DateTasks) extras.getSerializable(Constants.DATE_DATA);
            int taskPos = extras.getInt(Constants.TASK_POS);

            if(taskActionType.equals(Constants.TASK_ACTION_TYPE_VIEW)) {
                fragment = new ScreenSlidePagerActivity(dateTasks, taskPos);
            }
            else {
                fragment = new ManageTaskFragment();
            }
            tasksViewModel.setTaskPos(taskPos);
            tasksViewModel.setDateTasks(dateTasks);

            bundle.putString(Constants.TASK_ACTION_TYPE, taskActionType);
            fragment.setArguments(bundle);
            transaction.add(R.id.taskFragment, fragment, "fragment_tag");
            transaction.commit();
        }

    }

    private void initListeners() {
        editTaskViewModel.getTaskToEdit().observe(this, task -> {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                Fragment fragment = new ManageTaskFragment();
                bundle.putString(Constants.TASK_ACTION_TYPE, Constants.TASK_ACTION_TYPE_EDIT);
                fragment.setArguments(bundle);
                transaction.replace(R.id.taskFragment, fragment);
                transaction.commit();
        });
    }
}