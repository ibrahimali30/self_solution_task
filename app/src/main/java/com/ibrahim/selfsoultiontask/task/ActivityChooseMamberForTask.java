package com.ibrahim.selfsoultiontask.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Task;
import com.ibrahim.selfsoultiontask.model.TeamMember;
import com.ibrahim.selfsoultiontask.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ActivityChooseMamberForTask extends AppCompatActivity implements TeamMembersAdapter.OnmemberClicked {
    private static final String TAG = "ActivityChooseMamberFor";
    List<TeamMember> mTeamMembers;

    //view
    RecyclerView mRecyclerView;
    TeamMembersAdapter mAdapter;
    private FirebaseDatabase database;
    private String projectUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mamber_for_task);

        intRecyclerView();

        FirebaseDatabase.getInstance().getReference().child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot);
                    TeamMember teamMember = snapshot.getValue(TeamMember.class);
                    mTeamMembers.add( teamMember);
                }
                mAdapter.setTeamMembers(mTeamMembers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intRecyclerView() {
        mRecyclerView = findViewById(R.id.member_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTeamMembers = new ArrayList<>();
        mAdapter = new TeamMembersAdapter(mTeamMembers , this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMemberClicked(TeamMember teamMember) {
        Intent intent = new Intent();
        intent.putExtra("member_email" , teamMember.getEmail());

        setResult(RESULT_OK , intent);
        finish();
    }
}
