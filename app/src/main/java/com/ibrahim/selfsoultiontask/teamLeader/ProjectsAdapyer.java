package com.ibrahim.selfsoultiontask.teamLeader;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Project;

import java.util.List;

public class ProjectsAdapyer extends RecyclerView.Adapter<ProjectsAdapyer.ProjectViewHolder> {
    private static final String TAG = "ProjectsAdapyer";
    List<Project> mProjects ;
    private OnProjectItemClicked mItemClicked;

    public interface OnProjectItemClicked{
        void onProjectItemClicked(Project project);
    }

    public ProjectsAdapyer(List<Project> projects , OnProjectItemClicked onProjectItemClicked) {
        mProjects = projects ;
        this.mItemClicked = onProjectItemClicked ;
    }

    public void setProjects(List<Project> projects) {
        mProjects = projects;
        notifyDataSetChanged();
        Log.d(TAG, "setProjects: called +"+mProjects.size());
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_holder, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Project project = mProjects.get(position);
        holder.projectTitle.setText(project.getTitle());
        holder.description.setText(project.getDescription());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+mProjects.size());
        return mProjects.size();
    }


     class ProjectViewHolder extends RecyclerView.ViewHolder{

         TextView projectTitle , description;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle  = itemView.findViewById(R.id.project_title);
            description  = itemView.findViewById(R.id.project_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClicked.onProjectItemClicked(mProjects.get(getAdapterPosition()));
                }
            });
        }
    }
}
