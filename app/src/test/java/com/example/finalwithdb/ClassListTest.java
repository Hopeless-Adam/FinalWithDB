package com.example.finalwithdb;

import static org.junit.Assert.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

public class ClassListTest {

    @Test
    public void onCreate() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Classes");

        assertEquals(myRef, "https://finalwithdb-default-rtdb.firebaseio.com/Classes");
    }
}