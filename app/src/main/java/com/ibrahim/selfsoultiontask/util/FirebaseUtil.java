package com.ibrahim.selfsoultiontask.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ibrahim.selfsoultiontask.model.Project;
import com.ibrahim.selfsoultiontask.model.Task;
import com.ibrahim.selfsoultiontask.task.ProjectActivity;

public class FirebaseUtil {
    private static final String TAG = "FirebaseUtil";
    public static String getUserUid(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static void addNewProject(final String name, String description , final Context context) {
        FirebaseDatabase.getInstance().getReference().child("projects").child(getUserUid())
                .push().setValue(new Project(name , description).asMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "project "+name+" was added successefully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addNewTask(String memberEmail, String projectUid, String title
            , String content, String deadline, String expectedWorkingHours, final Context context) {

        Log.d(TAG, "addNewTask: "+new Task( content , deadline  , expectedWorkingHours , title , memberEmail).asMap());
        FirebaseDatabase.getInstance().getReference().child("tasks").child(projectUid).push()
                        .setValue(new Task( content , deadline  , expectedWorkingHours , title , memberEmail).asMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "new task added successefully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void deleteTask(Task task, String projectUid , final Context projectActivity) {
        FirebaseDatabase.getInstance().getReference().child("tasks").child(projectUid).child(task.getTaskUid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(projectActivity, "task deleted successefully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
