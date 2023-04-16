package com.example.mobiletaskplanner.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;

import java.util.function.Consumer;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {

    private final Consumer<Task> onTaskClicked;

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> onTaskClicked) {
        super(diffCallback);
        this.onTaskClicked = onTaskClicked;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskAdapter.ViewHolder(view, parent.getContext(), position -> {
            Task task = getItem(position);
            onTaskClicked.accept(task);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task Task = getItem(position);
        holder.bind(Task);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

        public void bind(Task task) {
//            Toast.makeText(context, task.getTitle(), Toast.LENGTH_SHORT).show();
            TextView taskTv = itemView.findViewById(R.id.taskTv);
            taskTv.setText(String.valueOf(task.getTitle()));
        }

    }

}