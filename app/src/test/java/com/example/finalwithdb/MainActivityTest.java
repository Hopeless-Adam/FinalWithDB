package com.example.finalwithdb;

import static org.junit.Assert.*;

import com.example.finalwithdb.Calendar.CalendarUtils;

import org.junit.Test;

import java.time.LocalDate;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void previousMonthAction() {
    }

    @Test
    public void nextMonthAction() {
    }

    @Test
    public void onItemClick() {
        assertEquals(CalendarUtils.selectedDate, LocalDate.now());
    }

    @Test
    public void weeklyAction() {
    }
}