package com.example.mobiletaskplanner.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.listeners.SwipeDetector;
import com.example.mobiletaskplanner.listeners.SwipeGestureListener;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.viewmodels.EditTaskViewModel;
import com.example.mobiletaskplanner.view.viewmodels.SharedViewModel;
import com.example.mobiletaskplanner.view.viewmodels.TasksViewModel;

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
    private TasksViewModel tasksViewModel;

    private GestureDetectorCompat gestureDetector;

    public ViewTaskFragment() {
        super(R.layout.fragment_view_task);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_task, container, false);
//        gestureDetector = new GestureDetectorCompat(getActivity(), new SwipeGestureListener(new SwipeGestureListener.OnSwipeListener() {
//            @Override
//            public void onSwipeLeft() {
//                tasksViewModel.setTaskPos(tasksViewModel.getTaskPos().getValue() - 1);
//                setupView();
//            }
//
//            @Override
//            public void onSwipeRight() {
//                tasksViewModel.setTaskPos(tasksViewModel.getTaskPos().getValue() + 1);
//                setupView();
//            }
//        }));
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        SwipeDetector swipeDetector = new SwipeDetector();
        view.setOnTouchListener(swipeDetector);

        if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
            Log.e("SWIPE", "LEFT TO RIGHT");
            Toast.makeText(getActivity(), "LEFT TO RIGHT", Toast.LENGTH_SHORT).show();
            //Do some action
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTaskViewModel = new ViewModelProvider(requireActivity()).get(EditTaskViewModel.class);
        tasksViewModel = new ViewModelProvider(getActivity()).get(TasksViewModel.class);

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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent();
                                intent.putExtra(Constants.TASK_CODE, Constants.TASK_CODE_REMOVE);
                                intent.putExtra(Constants.TASK_DATA, task);
                                getActivity().setResult(Constants.RESULT_OK, intent);
                                getActivity().finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getContext().getString(R.string.are_you_sure)).setPositiveButton(getContext().getString(R.string.yes), dialogClickListener).setNegativeButton(getContext().getString(R.string.no), dialogClickListener).show();

            }
        });

    }

    private void setupView() {
        dateTasks = tasksViewModel.getDateTasks().getValue();
        task = dateTasks.getTasks().get(tasksViewModel.getTaskPos().getValue());
        taskDate.setText(Util.formatMonthDay(dateTasks, getContext()));
        taskTitle.setText(task.getTitle());
        taskTime.setText(Util.formatTimeHourMinute(task));
        taskDescription.setText(task.getDescription());
    }
}