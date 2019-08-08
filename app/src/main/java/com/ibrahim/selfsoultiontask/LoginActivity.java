package com.ibrahim.selfsoultiontask;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ibrahim.selfsoultiontask.teamLeader.TeamLeaderMain;
import com.ibrahim.selfsoultiontask.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    EditText emailEditText;
    EditText passEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");

        getSupportActionBar().hide();

        ArrayList<String> strings = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for(String s : strings) {
            builder.append(s);
        }
        String str = builder.toString();

        //initialize widget
        Button login = findViewById(R.id.loginButton);
        Button register = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.PassEditText);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();
                Log.d(TAG, "onClick: "+email+password);
                //todo add validation method

                //check for empty fields
                if (password.length()== 0 || email.length()== 0){
                    Log.d(TAG, "onClick: pas"+password+" email f sdfr rf "+email);
                    showAlertDialoge("empty field" , "please enter a valid E-mail and Password");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    addMember();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    showAlertDialoge("Authentication failed" , "please enter a valid E-mail and Password");
                                }

                            }
                        });


            }
        });

        //jump to register activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext() , RegisterNewUser.class);
                startActivity( intent );
            }
        });



    }

    private void addMember() {
        Map<String , String> map = new HashMap<>();
        map.put("uid" , FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("email" , FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FirebaseDatabase.getInstance().getReference().child("members").child(FirebaseUtil.getUserUid()).setValue(map);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null){
            Intent intent = new Intent(getApplicationContext() , TeamLeaderMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


    private void showAlertDialoge(String title , String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        // add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setIcon(android.R.drawable.ic_dialog_alert);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void forgetPassword(View view) {
        Intent intent = new Intent(this , ResetPasswordActivity.class);
        startActivity(intent);
    }
}
