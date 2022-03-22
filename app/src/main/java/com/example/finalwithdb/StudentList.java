package com.example.finalwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StudentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ClassKey = extras.getString("key");
            System.out.println(ClassKey);
            //The key argument here must match that used in the other activity
        }


    }
}