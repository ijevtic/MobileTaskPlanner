package com.example.mobiletaskplanner.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mobiletaskplanner.models.Task;

public class TaskDiffItemCallback extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getStartTimeMinutes() == newItem.getStartTimeMinutes() &&
                oldItem.getEndTimeMinutes() == newItem.getEndTimeMinutes();
    }
}