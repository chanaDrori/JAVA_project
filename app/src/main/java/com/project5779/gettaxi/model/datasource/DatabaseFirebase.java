package com.project5779.gettaxi.model.datasource;

import android.content.ContentValues;

import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.entities.Drive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseFirebase implements DBmanager{
    @Override
    public void addNewDrive(ContentValues contentValues) throws Exception {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
