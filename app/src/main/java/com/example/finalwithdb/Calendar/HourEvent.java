package com.example.finalwithdb.Calendar;

import com.example.finalwithdb.Calendar.Event;

import java.time.LocalTime;
import java.util.ArrayList;

/*
The code for the calendar feature was created by GitHub user "codeWithCal". The link to the git repository is
https://github.com/codeWithCal/CalendarTutorialAndroidStudio.
The YouTube tutorial followed is
https://www.youtube.com/playlist?list=PLnQbggnVfvo2_VKtkYhrbRz25FjQn4ZIi
 */

class HourEvent
{
    LocalTime time;
    ArrayList<Event> events;

    public HourEvent(LocalTime time, ArrayList<Event> events)
    {
        this.time = time;
        this.events = events;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public void setEvents(ArrayList<Event> events)
    {
        this.events = events;
    }
}