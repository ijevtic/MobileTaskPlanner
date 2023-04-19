package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;

public class TasksViewModel extends ViewModel {

    private final MutableLiveData<DateTasks> dateTasks = new MutableLiveData<>();

    private final MutableLiveData<Task> task = new MutableLiveData<>();

    public LiveData<DateTasks> getDateTasks() {
        return dateTasks;
    }

    public LiveData<Task> getTask() {
        return task;
    }

    public void setDateTasks(DateTasks newValue) {
        dateTasks.setValue(newValue);
    }

    public void setTask(Task newValue) {
        task.setValue(newValue);
    }
}
