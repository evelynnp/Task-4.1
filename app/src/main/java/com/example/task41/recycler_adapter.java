package com.example.task41;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.TaskViewHolder> {
    private List<Task> task_list;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public recycler_adapter(Context context, ArrayList<Task> task_list) {
        this.context = context;
        this.task_list = task_list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_row,parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = task_list.get(position);
        holder.title.setText(task.getTitle());
        holder.date.setText(task.getDate());
        holder.description.setText(task.getDescription());

        holder.itemView.setOnClickListener(view -> {
           if (listener != null) {
               listener.onItemClick(position);
           }
        });
    }

    @Override
    public int getItemCount() {
        return task_list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, description;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            date = itemView.findViewById(R.id.taskDate);
            description = itemView.findViewById(R.id.taskDescription);
        }
    }
}
