package com.ibrahim.selfsoultiontask.task;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Task;
import com.ibrahim.selfsoultiontask.util.Constants;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    List<Task> mTaskList = new ArrayList<>();
    int priviledgestatus;

    public TaskAdapter(List<Task> taskList, int status) {
        mTaskList = taskList;
        this.priviledgestatus = status;
    }

    public void setTaskList(List<Task> taskList ) {
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_holder , parent , false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = mTaskList.get(position);

        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(task.getTime()));
        String deadLine = prettyTime.format(new Date(Long.parseLong(task.getDeadLine())));

        holder.time.                setText("time           :"+ago);
        holder.deadline.            setText("dead line     :"+deadLine);
        holder.cotent.              setText("Contetn       :"+task.getContent());
        holder.expectinWorkingHours.setText("working hours :"+task.getExpectedWorkingHours());
        holder.title.setText(task.getTitle());


    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView title, cotent , deadline , time , expectinWorkingHours , status ;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            cotent = itemView.findViewById(R.id.content);
            deadline = itemView.findViewById(R.id.dead_line);
            time = itemView.findViewById(R.id.time);
            expectinWorkingHours = itemView.findViewById(R.id.expecting_hours);
            status = itemView.findViewById(R.id.status);

            if (priviledgestatus == Constants.CAN_EDIT_TASK){
                itemView.setOnCreateContextMenuListener(this);
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("select option");
            menu.add(this.getAdapterPosition() , Constants.CONTEXT_MENU_EDIT_TASK, 0 , "edit task");
            menu.add(this.getAdapterPosition() , Constants.CONTEXT_MENU_DELETET_TASK, 1 , "delete task");

        }
    }
}
