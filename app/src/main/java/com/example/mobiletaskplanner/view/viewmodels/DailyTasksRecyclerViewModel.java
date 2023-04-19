package com.example.mobiletaskplanner.view.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyTasksRecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    private DateTasks dateTasks = new DateTasks();
    private Map<TaskPriority, Boolean> priorityStates = new HashMap<>();
    private String searchQuery = "";
    private int uniqueId = 0;

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

    public void addTask(String title, int startTime, int endTime, String description, TaskPriority priority) {
        Task task = new Task(uniqueId++, title, startTime, endTime, description, priority);
        dateTasks.getTasks().add(task);
        prepareAndSubmitTasks();
    }

    public void addTask(Task task) {
        task.setId(uniqueId++);
        dateTasks.getTasks().add(task);
        prepareAndSubmitTasks();
    }

    public void editTask(Task task) {
        int taskIndex = dateTasks.getTasks().stream().filter(task1 -> task1.getId() == task.getId()).
                findFirst().map(dateTasks.getTasks()::indexOf).orElse(-1);

        if (taskIndex != -1) {
            Task t = dateTasks.getTasks().get(taskIndex);
            t.copyTask(task);
            prepareAndSubmitTasks();
        }
    }

    public void deleteTask(int id) {
        int taskIndex = dateTasks.getTasks().stream().filter(task -> task.getId() == id).
                findFirst().map(dateTasks.getTasks()::indexOf).orElse(-1);
        if(taskIndex != -1) {
            dateTasks.getTasks().remove(taskIndex);
            prepareAndSubmitTasks();
        }
    }

    public void changePriorityState(TaskPriority priority, boolean state) {
        priorityStates.put(priority, state);
        prepareAndSubmitTasks();
    }

    private void prepareAndSubmitTasks() {

        ArrayList<Task> listToSubmit = dateTasks.getTasks().stream()
                .filter(task -> priorityStates.get(task.getPriority()))
                .filter(task -> task.getTitle().toLowerCase().contains(searchQuery.toLowerCase()))
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



//    public Task getDateTasks(int id) {
//        Optional<Task> taskObject = allTasks.stream().filter(dateTasks -> dateTasks.() == id).findFirst();
//        return dateTasksObject.orElse(null);
//    }

//    public int getDateCount() {
//        return dates.size();
//    }

//    public void removeDateTasks(int id) {
//        Optional<Date> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//            cars.setValue(listToSubmit);
//        }
//    }
}

