package com.example.mobiletaskplanner.view.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.ArrayList;
import java.util.List;

public class DailyTasksRecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    private List<Task> tasks = new ArrayList<>();
    private int uniqueId = 0;

    public DailyTasksRecyclerViewModel() {

        List<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public LiveData<List<Task>> getMutableLiveDataTasks() {
        return mutableLiveDataTasks;
    }

    public void addTask(String title, int startTime, int endTime, String description, TaskPriority priority) {
        Task task = new Task(uniqueId++, title, startTime, endTime, description, priority);
        tasks.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public int addTask(Task task) {
        task.setId(uniqueId++);
        tasks.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
        return tasks.size() - 1;
    }

    public int editTask(Task task) {
        int taskIndex = tasks.stream().filter(task1 -> task1.getId() == task.getId()).
                findFirst().map(tasks::indexOf).orElse(-1);

        if (taskIndex != -1) {
            Task t = tasks.get(taskIndex);
            t.copyTask(task);
            ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
            mutableLiveDataTasks.setValue(listToSubmit);
        }

        return taskIndex;
    }

    public int deleteTask(int id) {
        int taskIndex = tasks.stream().filter(task -> task.getId() == id).
                findFirst().map(tasks::indexOf).orElse(-1);
        if(taskIndex != -1) {
            tasks.remove(taskIndex);
            ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
            mutableLiveDataTasks.setValue(listToSubmit);
        }
        return taskIndex;
    }



//    public Task getDateTasks(int id) {
//        Optional<Task> taskObject = tasks.stream().filter(dateTasks -> dateTasks.() == id).findFirst();
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

