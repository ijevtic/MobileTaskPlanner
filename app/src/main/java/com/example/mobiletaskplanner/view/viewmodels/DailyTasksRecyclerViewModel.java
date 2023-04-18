package com.example.mobiletaskplanner.view.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyTasksRecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    private List<Task> allTasks = new ArrayList<>();
    private Map<TaskPriority, Boolean> priorityStates = new HashMap<>();
    private int uniqueId = 0;

    public DailyTasksRecyclerViewModel() {

        List<Task> listToSubmit = new ArrayList<>(allTasks);
        priorityStates.put(TaskPriority.LOW, true);
        priorityStates.put(TaskPriority.MEDIUM, true);
        priorityStates.put(TaskPriority.HIGH, true);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public LiveData<List<Task>> getMutableLiveDataTasks() {
        return mutableLiveDataTasks;

    }

    public void addTask(String title, int startTime, int endTime, String description, TaskPriority priority) {
        Task task = new Task(uniqueId++, title, startTime, endTime, description, priority);
        allTasks.add(task);
        filterAndSubmitTasks();
    }

    public void addTask(Task task) {
        task.setId(uniqueId++);
        allTasks.add(task);
        filterAndSubmitTasks();
    }

    public void editTask(Task task) {
        int taskIndex = allTasks.stream().filter(task1 -> task1.getId() == task.getId()).
                findFirst().map(allTasks::indexOf).orElse(-1);

        if (taskIndex != -1) {
            Task t = allTasks.get(taskIndex);
            t.copyTask(task);
            filterAndSubmitTasks();
        }
    }

    public void deleteTask(int id) {
        int taskIndex = allTasks.stream().filter(task -> task.getId() == id).
                findFirst().map(allTasks::indexOf).orElse(-1);
        if(taskIndex != -1) {
            allTasks.remove(taskIndex);
            filterAndSubmitTasks();
        }
    }

    public void changePriorityState(TaskPriority priority, boolean state) {
        priorityStates.put(priority, state);
        filterAndSubmitTasks();
    }

    private void filterAndSubmitTasks() {

        ArrayList<Task> listToSubmit = allTasks.stream()
                .filter(task -> priorityStates.get(task.getPriority()))
                .collect(Collectors.toCollection(ArrayList::new));

        mutableLiveDataTasks.setValue(listToSubmit);
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

