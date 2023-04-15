package com.example.mobiletaskplanner.view.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobiletaskplanner.models.DateTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecyclerViewModel extends ViewModel {

    public static int counter = 101;

    private final MutableLiveData<List<DateTasks>> mutableLiveDataDates = new MutableLiveData<>();
    private List<DateTasks> dates = new ArrayList<>();

    public RecyclerViewModel() {
        for(int i = 0; i < 10; i++) {
            dates.add(new DateTasks(i, "Task " + i));
        }

        List<DateTasks> listToSubmit = new ArrayList<>(dates);
        mutableLiveDataDates.setValue(listToSubmit);
    }

    public LiveData<List<DateTasks>> getMutableLiveDataDates() {
        return mutableLiveDataDates;
    }

    public void addDateTasks() {
        int id = counter++;
        DateTasks dateTasks = new DateTasks(id, "date " + id);
        dates.add(dateTasks);
        ArrayList<DateTasks> listToSubmit = new ArrayList<>(dates);
        mutableLiveDataDates.setValue(listToSubmit);
    }

//    public void removeDateTasks(int id) {
//        Optional<Date> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//            cars.setValue(listToSubmit);
//        }
//    }

}
