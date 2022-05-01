package com.example.finalwithdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.finalwithdb.Calendar.CalendarUtils;
import com.example.finalwithdb.Calendar.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class ClassList extends AppCompatActivity {

    ListView ClassListView;

    ArrayList<String> ClassNameArray =new ArrayList<>();
    ArrayList<String> ClassKeyArray = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Classes");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        System.out.println(myRef);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.page_1:
                        Intent intent = new Intent(ClassList.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return false;
            }
        });




        Button NewClassButton = (Button) findViewById(R.id.button19);
        NewClassButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassList.this, PopUpForClasslist.class);
                startActivity(intent);
            }
        }));

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                        {

                            ClassNameArray.add(data.getKey());
                            ClassKeyArray.add(data.getKey());

                        }
                    }
                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });




        setClassAdapter();
    }
    private void setClassAdapter()
    {
        ClassListView = findViewById(R.id.ClassListView);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ClassNameArray);
        ClassListView.setAdapter(arr);
        ClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ItemAtPos = (String) ClassListView.getItemAtPosition((int) l);
                String KeyAtPos = ClassKeyArray.get((int) l);
                //System.out.println(ClassKeyArray.get((int) l));
                Intent intent = new Intent(ClassList.this, StudentList.class);
                intent.putExtra("key", KeyAtPos);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setClassAdapter();
    }
}