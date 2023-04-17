package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.Task;

public class EditTaskViewModel extends ViewModel {

    private final MutableLiveData<Task> taskToEdit = new MutableLiveData<>();

    public LiveData<Task> getTaskToEdit() {
        return taskToEdit;
    }

    public void storeTask(Task task) {
        taskToEdit.setValue(task);
    }

}