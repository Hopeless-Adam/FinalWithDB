package com.example.finalwithdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndividualPage extends AppCompatActivity {

    ArrayList<String> AssignmentString =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_page);

        TextView NameTextView = findViewById(R.id.StudentName);

        Button NewAssignmentButton = findViewById(R.id.button6);
        Button SaveButton = findViewById(R.id.button7);

        EditText StudentDescriptionTextBox = findViewById(R.id.editTextTextMultiLine);

        String StudentName = new String();
        String ClassName = new String();

        String AssignmentName = new String();
        String AssignmentGrade = new String();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StudentName = extras.getString("key");
            ClassName = extras.getString("key2");
            //The key argument here must match that used in the other activity
        }

        NameTextView.setText(StudentName);

        String finalStudentName = StudentName;
        String finalClassName = ClassName;


        NewAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndividualPage.this, PopUpForIndividual.class);
                intent.putExtra("key", finalStudentName);
                intent.putExtra("key2", finalClassName);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Classes/"+ finalClassName +"/" + finalStudentName + "/Assignments");

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                        {
                            AssignmentString.add((String) data.getValue());
                        }
                    }

                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });



        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference("Classes/"+ finalClassName +"/" + finalStudentName);
                String StudentDescription = StudentDescriptionTextBox.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("Description for " + finalStudentName,StudentDescription);
                myRef.updateChildren(map);

            }
        });


        DatabaseReference myRefForEditText = database.getReference("Classes/"+ finalClassName +"/" + finalStudentName);
        myRefForEditText.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    StudentDescriptionTextBox.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });


        setIndividualAdapter();
    }

    private void setIndividualAdapter() {
        ListView AssignmentListView = findViewById(R.id.AssignmentListView);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,AssignmentString);
        AssignmentListView.setAdapter(arr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIndividualAdapter();
    }

}