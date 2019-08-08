package com.ibrahim.selfsoultiontask.task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ibrahim.selfsoultiontask.LoginActivity;
import com.ibrahim.selfsoultiontask.R;
import com.ibrahim.selfsoultiontask.model.Task;
import com.ibrahim.selfsoultiontask.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddNewTaskActivity extends AppCompatActivity {

    EditText title , content , deadline , workinHours , member;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        deadline = findViewById(R.id.dead_line);
        workinHours = findViewById(R.id.working_hours);
        member = findViewById(R.id.submit_task_to);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddNewTaskActivity.this , ActivityChooseMamberForTask.class) , Constants.CHOOSE_MAMBER);
            }
        });

        if (getIntent().getIntExtra("mode" , -1) == Constants.EdIT_MODE){
            fillFields();
        }

       initDeadLine();

        Button button = findViewById(R.id.add_task_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              finishActivityAndSendExtras();
            }
        });
    }

    private void fillFields() {
        Task taskToEDit = (Task) getIntent().getSerializableExtra("task");
        title.setText(taskToEDit.getTitle());
        member.setText(taskToEDit.getMeberEmail());
        content.setText(taskToEDit.getContent());
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        deadline.setText(sdf.format(taskToEDit.getTime()));

        workinHours.setText(taskToEDit.getExpectedWorkingHours());
    }

    private void finishActivityAndSendExtras() {
        if (title.getText().length()==0 ||
                content.getText().length()==0 ||
                deadline.getText().length()==0 ||
                workinHours.getText().length()==0)
        {
            Toast.makeText(AddNewTaskActivity.this, "fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.NEW_TASK_TITLE , title.getText().toString());
        intent.putExtra(Constants.NEW_TASK_CONTENT , content.getText().toString());
        intent.putExtra(Constants.NEW_TASK_DADLINE , String.valueOf(myCalendar.getTimeInMillis()));
        intent.putExtra(Constants.NEW_TASK_WORKING_HOURS , workinHours.getText().toString());
        intent.putExtra("member" , member.getText().toString());

        setResult(RESULT_OK , intent);

        finish();
    }

    private void initDeadLine() {
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                deadline.setText(sdf.format(myCalendar.getTime()));
            }

        };

        deadline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.CHOOSE_MAMBER && resultCode == RESULT_OK){
            member.setText(data.getStringExtra("member_email"));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
