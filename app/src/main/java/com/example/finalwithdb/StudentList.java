package com.example.finalwithdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentList extends AppCompatActivity {

    ArrayList<String> StudNameArray =new ArrayList<>();

    ListView StudListView;
    String ClassKey = new String();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


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

        Button RemoveClass = findViewById(R.id.button4);
        RemoveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Classes/"+ finalClassKey);
                myRef.removeValue();
                Intent intent = new Intent(StudentList.this , ClassList.class);
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

                            StudNameArray.add(data.getKey());

                        }

                    }

                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });

        /*myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                   // StudNameArray.add(String.valueOf(task.getResult().getValue()));
                }
            }
        });*/



        setStudentAdapter();

    }

    private void setStudentAdapter() {
        StudListView = findViewById(R.id.StudListView);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, StudNameArray);
        StudListView.setAdapter(arr);
        StudListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String StudAtPos = StudNameArray.get((int) l);
            Intent intent = new Intent(StudentList.this, IndividualPage.class);
            intent.putExtra("key", StudAtPos);
            intent.putExtra("key2", ClassKey);
            startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setStudentAdapter();
    }
}