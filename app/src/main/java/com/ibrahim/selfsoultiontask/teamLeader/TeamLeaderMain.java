package com.ibrahim.selfsoultiontask.teamLeader;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibrahim.selfsoultiontask.LoginActivity;
import com.ibrahim.selfsoultiontask.task.ProjectActivity;
import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Project;
import com.ibrahim.selfsoultiontask.util.Constants;
import com.ibrahim.selfsoultiontask.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

public class TeamLeaderMain extends AppCompatActivity implements
        ProjectsAdapyer.OnProjectItemClicked,
        AddNewProjectFragment.OnAddNewHags {

    private static final String TAG = "TeamLeaderMain";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;

    List<Project> mProjectList;
    //view
    RecyclerView mRecyclerView;
    ProjectsAdapyer mProjectsAdapyer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Log.d(TAG, "onCreate: started");
        setUpFireBaseListener();
        initRecyclerView();

        database = FirebaseDatabase.getInstance();

        database.getReference().child("projects").child(FirebaseUtil.getUserUid()).orderByChild("date")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mProjectList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Project project = snapshot.getValue(Project.class);
                            project.setUid(snapshot.getKey());
                            mProjectList.add(project);
                        }

                        Log.d(TAG, "onDataChange: "+mProjectList);
                        mProjectsAdapyer.setProjects(mProjectList);
                        mProjectsAdapyer.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.projects_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mProjectList = new ArrayList<>();
        mProjectsAdapyer = new ProjectsAdapyer(mProjectList , this);
        mRecyclerView.setAdapter(mProjectsAdapyer);
    }


    private void setUpFireBaseListener(){
        Log.d(TAG, "setUpFireBaseListener: setting up the auth state listener");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mFirebaseAuth.getCurrentUser();

                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: signed in "+user.getUid());

                }else {
                    Log.d(TAG, "onAuthStateChanged:signed out ");
                    Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
                    //reset activity life cycle
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.log_out :
                FirebaseAuth.getInstance().signOut();
                break;
                
            case R.id.add_project:

                AddNewProjectFragment newProjectFragment = new AddNewProjectFragment();
                newProjectFragment.show(getSupportFragmentManager() , "add new fragment");

        }
       
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //remove AuthState Listener
        if (mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onProjectItemClicked(Project project) {
        Log.d(TAG, "onProjectItemClicked: clicked");
        Intent intent = new Intent(this , ProjectActivity.class);
        intent.putExtra(Constants.PRJECT_TITLE , project.getTitle());
        intent.putExtra(Constants.PRJECT_UID , project.getUid());
        startActivity(intent);
    }

    @Override
    public void onAddNewProject(String name, String description) {
        FirebaseUtil.addNewProject(name , description , this) ;
    }
}
