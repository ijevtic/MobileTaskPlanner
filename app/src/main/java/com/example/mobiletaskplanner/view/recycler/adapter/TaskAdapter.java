package com.example.mobiletaskplanner.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.Task;

import java.util.function.Consumer;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onDelete(Task task);
        void onEdit(Task task);
    }

    private final Consumer<Task> onTaskClicked;
    private OnItemClickListener listener;

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> onTaskClicked, OnItemClickListener listener) {
        super(diffCallback);
        this.onTaskClicked = onTaskClicked;
        this.listener = listener;
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
        Task task = getItem(position);
        holder.bind(task, holder, listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private TextView taskTv;
        private TextView editTask;
        private TextView deleteTask;
        private Task task;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());

                }
            });
        }

        public void bind(Task task, @NonNull TaskAdapter.ViewHolder holder, OnItemClickListener listener) {
            this.task = task;
            initView();
            initListeners(holder, listener);
            setupView();
        }

        private void initView() {
            taskTv = itemView.findViewById(R.id.taskTv);
            editTask = itemView.findViewById(R.id.ti_edit_task);
            deleteTask = itemView.findViewById(R.id.ti_delete_task);
        }

        private void initListeners(@NonNull TaskAdapter.ViewHolder holder, OnItemClickListener listener) {
            holder.editTask.setOnClickListener(v -> {
                listener.onEdit(task);
            });

            holder.deleteTask.setOnClickListener(v -> {
                listener.onDelete(task);
            });


        }

        private void setupView() {
            taskTv.setText(String.valueOf(task.getTitle()));
        }

    }

}