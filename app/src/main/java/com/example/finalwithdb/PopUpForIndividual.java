package com.example.finalwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalwithdb.Calendar.CalendarUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PopUpForIndividual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_for_individual);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        String ClassKey = new String();
        String StudName = new String();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ClassKey = extras.getString("key2");
            StudName = extras.getString("key");
            //The key argument here must match that used in the other activity
        }





        EditText AssignmentName = findViewById(R.id.editTextTextPersonName2);
        EditText GradeValue = findViewById(R.id.editTextTextPersonName3);

        Button SubmitButton = findViewById(R.id.button3);

        String finalClassKey = ClassKey;
        String finalStudName = StudName;

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AssignmentNameText = AssignmentName.getText().toString();
                String GradeValueText = GradeValue.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Classes/"+ finalClassKey +"/" + finalStudName + "/Assignments");

                String uniqueID = UUID.randomUUID().toString();

                String AssignmentString = AssignmentNameText + " " + GradeValueText;


                Map<String,Object> map = new HashMap<>();
                map.put(uniqueID,AssignmentString);

                myRef.updateChildren(map);

                finish();


            }
        });




    }
}