package com.example.mobiletaskplanner.view.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.db.DBHelper;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyTasksRecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    private DateTasks dateTasks = new DateTasks();
    private Map<TaskPriority, Boolean> priorityStates = new HashMap<>();
    private String searchQuery = "";
    private boolean showPastObligations = false;
    private int currentTimeMinutes;
    private int uniqueId = 0;

    private DBHelper dbHelper;

    public DailyTasksRecyclerViewModel() {

        priorityStates.put(TaskPriority.LOW, true);
        priorityStates.put(TaskPriority.MEDIUM, true);
        priorityStates.put(TaskPriority.HIGH, true);
        List<Task> listToSubmit = new ArrayList<>(dateTasks.getTasks());
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public LiveData<List<Task>> getMutableLiveDataTasks() {
        return mutableLiveDataTasks;
    }

    public void createDbHelper(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void addTask(String title, int startTime, int endTime, String description, TaskPriority priority) {
        Task task = new Task(uniqueId++, title, startTime, endTime, description, priority);
        dateTasks.getTasks().add(task);
        prepareAndSubmitTasks();
        saveState();
    }

    public void addTask(Task task) {
        task.setId(uniqueId++);
        dateTasks.getTasks().add(task);
        prepareAndSubmitTasks();
        saveState();
    }

    public void editTask(Task task) {
        int taskIndex = dateTasks.getTasks().stream().filter(task1 -> task1.getId() == task.getId()).
                findFirst().map(dateTasks.getTasks()::indexOf).orElse(-1);

        if (taskIndex != -1) {
            Task t = dateTasks.getTasks().get(taskIndex);
            t.copyTask(task);
            prepareAndSubmitTasks();
            saveState();
        }
    }

    public void deleteTask(int id) {
        int taskIndex = dateTasks.getTasks().stream().filter(task -> task.getId() == id).
                findFirst().map(dateTasks.getTasks()::indexOf).orElse(-1);
        if(taskIndex != -1) {
            dateTasks.getTasks().remove(taskIndex);
            prepareAndSubmitTasks();
            saveState();

        }
    }

    public void changePriorityState(TaskPriority priority, boolean state) {
        priorityStates.put(priority, state);
        prepareAndSubmitTasks();
    }

    private void prepareAndSubmitTasks() {

        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.HOUR_OF_DAY);
        currentTimeMinutes = currentHour * 60 + currentMinute;
//        if(dateTasks == null)
//            return;

        ArrayList<Task> listToSubmit = dateTasks.getTasks().stream()
                .filter(task -> priorityStates.get(task.getPriority()))
                .filter(task -> task.getTitle().toLowerCase().contains(searchQuery.toLowerCase()))
                .filter(task -> showPastObligations || task.getEndTimeMinutes() >= currentTimeMinutes)
                .sorted(Task::compareTo)
                .collect(Collectors.toCollection(ArrayList::new));

        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public void changeDateTasks(DateTasks dateTasks) {
        this.dateTasks = dateTasks;
        prepareAndSubmitTasks();
    }

    public void changeSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery == null ? "" : searchQuery;
        prepareAndSubmitTasks();
    }

    public void changePastObligationsState(boolean showPastObligations) {
        this.showPastObligations = showPastObligations;
        prepareAndSubmitTasks();
    }

    private void saveState() {
        dbHelper.saveTasks(dateTasks);
    }

    public List<Task> test(Long unixTime) {
        return dbHelper.loadTasks(unixTime);
    }
}

