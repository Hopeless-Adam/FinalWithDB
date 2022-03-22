package com.example.finalwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PopUpForStudentlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_for_studentlist);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        EditText TextEdit = findViewById(R.id.editTextTextPersonName2);

        Button SubmitButton = findViewById(R.id.button3);

        String ClassKey = new String();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ClassKey = extras.getString("key");
            //The key argument here must match that used in the other activity
        }


        String finalClassKey = ClassKey;
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String uniqueID = UUID.randomUUID().toString();

                String TextEditText = TextEdit.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Classes/"+finalClassKey);

                Map<String,Object> map = new HashMap<>();
                map.put(uniqueID,TextEditText);

                myRef.updateChildren(map);

                finish();
            }
        });

    }
}