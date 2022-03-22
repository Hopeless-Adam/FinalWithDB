package com.example.finalwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    ArrayList<String> StudNameArray =new ArrayList<>();

    ListView StudListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        String ClassKey = new String();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ClassKey = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        Button NewStud = findViewById(R.id.button);
        String finalClassKey = ClassKey;
        NewStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentList.this , PopUpForStudentlist.class);
                intent.putExtra("key", finalClassKey);
                startActivity(intent);
            }
        });

        System.out.println(ClassKey);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Classes/"+ finalClassKey);

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                        {

                            StudNameArray.add((String) data.getValue());

                        }
                        System.out.println("****" + StudNameArray);

                    }

                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });

        setStudentAdapter();

    }

    private void setStudentAdapter() {
        StudListView = findViewById(R.id.StudListView);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, StudNameArray);
        StudListView.setAdapter(arr);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setStudentAdapter();
    }
}