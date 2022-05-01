package com.example.finalwithdb.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalwithdb.Calendar.CalendarUtils;
import com.example.finalwithdb.Calendar.Event;
import com.example.finalwithdb.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
The code for the calendar feature was created by GitHub user "codeWithCal". The link to the git repository is
https://github.com/codeWithCal/CalendarTutorialAndroidStudio.
The YouTube tutorial followed is
https://www.youtube.com/playlist?list=PLnQbggnVfvo2_VKtkYhrbRz25FjQn4ZIi
 */

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);

        String uniqueID = UUID.randomUUID().toString();

        //List<String> EventList = new ArrayList<String>();
        String EventString = eventName + " " + CalendarUtils.selectedDate.toString() + " " + time.toString();
        //EventList.add(EventString);

        //System.out.println(" **** " + EventList.size());


        //System.out.println(uniqueID + EventString);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");

        //myRef.child(uniqueID).setValue(EventList);
        Map<String,Object> map = new HashMap<>();
        map.put(uniqueID, EventString);

        //myRef.setValue(EventList);
        myRef.updateChildren(map);

        finish();
    }
}