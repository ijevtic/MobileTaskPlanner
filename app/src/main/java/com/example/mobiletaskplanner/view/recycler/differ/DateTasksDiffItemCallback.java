package com.example.mobiletaskplanner.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mobiletaskplanner.models.DateTasks;

public class DateTasksDiffItemCallback extends DiffUtil.ItemCallback<DateTasks> {
    @Override
    public boolean areItemsTheSame(@NonNull DateTasks oldItem, @NonNull DateTasks newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DateTasks oldItem, @NonNull DateTasks newItem) {
        return oldItem.getDate().equals(newItem.getDate());
    }
}
