package com.ibrahim.selfsoultiontask.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.TeamMember;

import java.util.List;

class TeamMembersAdapter extends RecyclerView.Adapter<TeamMembersAdapter.MemberViewHolder> {

    List<TeamMember> mTeamMembers;
    OnmemberClicked mOnmemberClicked;

    public interface OnmemberClicked{
        void onMemberClicked(TeamMember teamMember);
    }


    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_holder , parent , false);
        return new MemberViewHolder(view);
    }

    public TeamMembersAdapter(List<TeamMember> teamMembers , OnmemberClicked onmemberClicked) {
        mTeamMembers = teamMembers;
        mOnmemberClicked = onmemberClicked;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        mTeamMembers = teamMembers;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.mTextView.setText(mTeamMembers.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mTeamMembers.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView ;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.member_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnmemberClicked.onMemberClicked(mTeamMembers.get(getAdapterPosition()));
                }
            });

        }
    }
}
