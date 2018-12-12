package com.project5779.gettaxi.model.datasource;

import android.content.ContentValues;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.project5779.gettaxi.R;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.entities.StateOfDrive;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFirebase implements DBmanager{

    public interface Action<T> {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }
    static DatabaseReference DriveRef;
    //static List<Drive> driveList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DriveRef = database.getReference("Drives");
       // driveList = new ArrayList<>();
    }

    @Override
    public void addNewDrive(final Drive newDrive, final Action<String> action) throws Exception {
      //  DriveRef.push().setValue(newDrive);
        addDriveToFirebase(newDrive, action);

    }

    private static void addDriveToFirebase(final Drive drive, final Action<String> action)
    {
        DriveRef.push().setValue(drive).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(drive.getNameClient());
                action.onProgress("upload drive data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload drive data", 100);
            }
        });
    }
}
