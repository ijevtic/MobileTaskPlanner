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
import com.example.mobiletaskplanner.models.DateTasks;

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
            TextView dateTv = itemView.findViewById(R.id.dateTv);
            dateTv.setText(dateTasks.getDate());
        }

    }

}
