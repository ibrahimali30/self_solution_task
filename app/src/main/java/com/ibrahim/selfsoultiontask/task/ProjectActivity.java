package com.ibrahim.selfsoultiontask.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Task;
import com.ibrahim.selfsoultiontask.util.Constants;
import com.ibrahim.selfsoultiontask.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectActivity extends AppCompatActivity {
    private static final String TAG = "ProjectActivity";

    List<Task> mTaskList;

    //view
    RecyclerView mRecyclerView;
    TaskAdapter mAdapter;
    private FirebaseDatabase database;
    private String projectUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        getSupportActionBar().setTitle(getIntent().getStringExtra(Constants.PRJECT_TITLE));

        intRecyclerView();

        projectUid = getIntent().getStringExtra(Constants.PRJECT_UID);

        database = FirebaseDatabase.getInstance();



       getTasksFromFireBase();

    }

    private void getTasksFromFireBase() {
        database.getReference().child("tasks").child(projectUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                mTaskList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        Task task = snapshot.getValue(Task.class);
                        task.setTaskUid(snapshot.getKey());
                        Log.d(TAG, "onDataChange: "+task);
                        mTaskList.add(task);
                }

                mAdapter.setTaskList(mTaskList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intRecyclerView() {
        mRecyclerView = findViewById(R.id.task_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTaskList = new ArrayList<>();
        mAdapter = new TaskAdapter(mTaskList , Constants.CAN_EDIT_TASK);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_task :
                startActivityForResult(new Intent(this , AddNewTaskActivity.class) , Constants.ADD_NEW_TASK);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_NEW_TASK && resultCode == RESULT_OK){
            String title = data.getStringExtra(Constants.NEW_TASK_TITLE);
            String content = data.getStringExtra(Constants.NEW_TASK_CONTENT);
            String deadline = data.getStringExtra(Constants.NEW_TASK_DADLINE);
            String expectedWorkingHours = data.getStringExtra(Constants.NEW_TASK_WORKING_HOURS);
            String member = data.getStringExtra("member");

            FirebaseUtil.addNewTask(member , projectUid , title , content , deadline , expectedWorkingHours ,  this);

            Log.d(TAG, "onActivityResult: -------------"+deadline);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final int itemPosition = item.getGroupId();

        switch (item.getItemId()){
            case Constants.CONTEXT_MENU_EDIT_TASK :
                Intent intent =new Intent(this , AddNewTaskActivity.class);
                intent.putExtra("mode" , Constants.EdIT_MODE);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", mTaskList.get(itemPosition));
                intent.putExtras(bundle);

                startActivityForResult( intent , Constants.ADD_NEW_TASK);
                break;

            case Constants.CONTEXT_MENU_DELETET_TASK :
                FirebaseUtil.deleteTask(mTaskList.get(itemPosition ), projectUid , this);
        }



        return super.onContextItemSelected(item);
    }
}
