package com.example.finalwithdb.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalwithdb.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
The code for the calendar feature was created by GitHub user "codeWithCal". The link to the git repository is
https://github.com/codeWithCal/CalendarTutorialAndroidStudio.
The YouTube tutorial followed is
https://www.youtube.com/playlist?list=PLnQbggnVfvo2_VKtkYhrbRz25FjQn4ZIi
 */

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    static class EventAdapter extends ArrayAdapter<Event>
    {
        public EventAdapter(@NonNull Context context, List<Event> events)
        {
            super(context, 0, events);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            Event event = getItem(position);

            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

            TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

            String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());
            eventCellTV.setText(eventTitle);
            return convertView;
        }
    }
}