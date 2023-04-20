package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;

public class PagerFragmentViewModel extends ViewModel {
    private final MutableLiveData<DateTasks> dateTasks = new MutableLiveData<>();

    public void setDateTasks(DateTasks dateTasks) {
        this.dateTasks.setValue(dateTasks);
    }

    public MutableLiveData<DateTasks> getDateTasks() {
        return dateTasks;
    }
}
