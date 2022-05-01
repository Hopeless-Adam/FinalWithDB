package com.example.finalwithdb.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalwithdb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.finalwithdb.Calendar.CalendarUtils.daysInWeekArray;
import static com.example.finalwithdb.Calendar.CalendarUtils.monthYearFromDate;

/*
The code for the calendar feature was created by GitHub user "codeWithCal". The link to the git repository is
https://github.com/codeWithCal/CalendarTutorialAndroidStudio.
The YouTube tutorial followed is
https://www.youtube.com/playlist?list=PLnQbggnVfvo2_VKtkYhrbRz25FjQn4ZIi
 */


public class WeekViewActivity extends AppCompatActivity implements CalendarUtils.CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Events");



    ArrayList<String> EventString =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();

        System.out.println("**" + EventString);

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                        {

                            EventString.add((String) data.getValue());

                        }
                        System.out.println("****" + EventString);

                    }

                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });


        setEventAdapter();


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarUtils.CalendarAdapter calendarAdapter = new CalendarUtils.CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);

        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, EventString);
        eventListView.setAdapter(arr);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }

}