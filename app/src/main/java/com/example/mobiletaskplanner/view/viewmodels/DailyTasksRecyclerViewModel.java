package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DailyTasksRecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    private List<Task> tasks = new ArrayList<>();
    private int uniqueId = 0;

    public DailyTasksRecyclerViewModel() {
//        for(int i = 0; i < 10; i++) {
//            dates.add(new DateTasks(i, "Task " + i));
//        }

        List<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public LiveData<List<Task>> getMutableLiveDataTasks() {
        return mutableLiveDataTasks;
    }

    public void addTask(String title, int startTime, int endTime, String description) {
        Task task = new Task(uniqueId++, title, startTime, endTime, description);
        tasks.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public void addTask(Task task) {
        task.setId(uniqueId++);
        tasks.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
        mutableLiveDataTasks.setValue(listToSubmit);
    }

    public void editTask(Task task) {
        Optional<Task> taskObject = tasks.stream().filter(task1 -> task1.getId() == task.getId()).findFirst();
        if (taskObject.isPresent()) {
            tasks.remove(taskObject.get());
            tasks.add(task);
            ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
            mutableLiveDataTasks.setValue(listToSubmit);
        }
    }

    public void deleteTask(int id) {
        Optional<Task> taskObject = tasks.stream().filter(task -> task.getId() == id).findFirst();
        if (taskObject.isPresent()) {
            tasks.remove(taskObject.get());
            ArrayList<Task> listToSubmit = new ArrayList<>(tasks);
            mutableLiveDataTasks.setValue(listToSubmit);
        }
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

