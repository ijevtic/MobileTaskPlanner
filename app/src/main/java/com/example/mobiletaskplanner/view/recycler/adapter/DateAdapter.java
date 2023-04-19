package com.example.mobiletaskplanner.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.models.TaskPriority;

import java.util.Map;
import java.util.function.Consumer;

public class DateAdapter extends ListAdapter<DateTasks, DateAdapter.ViewHolder> {

    private final Consumer<DateTasks> onDateClicked;

    public DateAdapter(@NonNull DiffUtil.ItemCallback<DateTasks> diffCallback, Consumer<DateTasks> onDateClicked) {
        super(diffCallback);
        this.onDateClicked = onDateClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            DateTasks date = getItem(position);
            onDateClicked.accept(date);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateTasks dateTasks = getItem(position);
        holder.bind(dateTasks);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Map<Integer, Integer> taskColorMap = Map.of(
                0, R.color.task_priority_low_disabled,
                1, R.color.task_priority_low,
                2, R.color.task_priority_medium,
                3, R.color.task_priority_high
        );

        private final Map<TaskPriority, Integer> taskPriorityMap = Map.of(
                TaskPriority.LOW, 1,
                TaskPriority.MEDIUM, 2,
                TaskPriority.HIGH, 3
        );

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(DateTasks dateTasks) {
//            Toast.makeText(context, dateTasks.getDate(), Toast.LENGTH_SHORT).show();
            TextView dateTv = itemView.findViewById(R.id.dateTv);
            dateTv.setText(String.valueOf(dateTasks.getDay()));
            int maxPriorityColor = 0;
            for(Task task: dateTasks.getTasks()) {
                maxPriorityColor = Math.max(maxPriorityColor, taskPriorityMap.get(task.getPriority()));
            }
//            int bgColor = ContextCompat.getColor(context, taskColorMap.get(maxPriorityColor));
            dateTv.setBackgroundResource(taskColorMap.get(maxPriorityColor));
        }

    }

}
