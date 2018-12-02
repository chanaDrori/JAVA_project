package com.project5779.gettaxi.model.datasource;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.entities.StateOfDrive;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFirebase implements DBmanager{

    //public static int numDrive = 0;
   /* public interface Action<T> {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }*/
    static DatabaseReference DriveRef;
    //static List<Drive> driveList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DriveRef = database.getReference("Drives");
       // driveList = new ArrayList<>();
    }

    @Override
    public void addNewDrive(ContentValues contentValues) throws Exception {
        Drive newDrive = ContentValuesToDrive(contentValues);
      //  DriveRef.child(String.valueOf(numDrive++)).setValue(newDrive);
        DriveRef.child(newDrive.getNameClient() + " " + newDrive.getStartPoint()).setValue(newDrive);
    }
    /*
    private static void addDriveToFirebase(final Drive drive, final Action<Long> action)
    {
        final String key = String.valueOf(numDrive++);
        DriveRef.child(key).setValue(drive).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess((long) numDrive);
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
*/
    public static Drive ContentValuesToDrive(ContentValues contentValues)
    {
        Drive drive = new Drive();
        drive.setNameClient(contentValues.getAsString(TaxiConst.DriveConst.NAME));
        drive.setPhoneClient(contentValues.getAsString(TaxiConst.DriveConst.PHONE));
        drive.setEmailClient(contentValues.getAsString(TaxiConst.DriveConst.EMAIL));
        drive.setStartPoint(contentValues.getAsString(TaxiConst.DriveConst.START_POINT));
        drive.setEndPoint(contentValues.getAsString(TaxiConst.DriveConst.END_POINT));
        drive.setStartTime(contentValues.getAsString(TaxiConst.DriveConst.START_TIME));
        drive.setEndTime(contentValues.getAsString(TaxiConst.DriveConst.END_TIME));
        drive.setState(StateOfDrive.valueOf(contentValues.getAsString(TaxiConst.DriveConst.STATE)));
        return drive;
    }
}
