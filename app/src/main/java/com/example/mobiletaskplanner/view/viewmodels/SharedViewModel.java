package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<DateTasks> selectedDate = new MutableLiveData<>();

    private final MutableLiveData<Boolean> refreshView = new MutableLiveData<>();

    public LiveData<DateTasks> getSelectedDate() {
        return selectedDate;
    }

    public void storeSelectedDate(DateTasks date) {
        selectedDate.setValue(date);
    }

    public LiveData<Boolean> getRefreshView() {
        return refreshView;
    }

    public void storeRefreshView(Boolean refresh) {
        refreshView.setValue(refresh);
    }
}