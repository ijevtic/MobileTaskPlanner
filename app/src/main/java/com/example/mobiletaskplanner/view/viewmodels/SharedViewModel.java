package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<DateTasks> selectedDate = new MutableLiveData<>();

    public LiveData<DateTasks> getSelectedDate() {
        return selectedDate;
    }

    public void storeSelectedDate(DateTasks date) {
        selectedDate.setValue(date);
    }

}