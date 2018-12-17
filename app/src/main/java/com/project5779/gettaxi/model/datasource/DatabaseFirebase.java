/**
 * Project in Java- Android.
 * Writers - Tirtza Raaya Rubinstain && Chana Drori
 * 12/2018
 * the class DatabaseFirebase implements the interface BackEnd and add the drive to Firebase.
 */

package com.project5779.gettaxi.model.datasource;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project5779.gettaxi.model.backend.BackEnd;
import com.project5779.gettaxi.model.entities.Drive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseFirebase implements BackEnd {

    /**
     * interface- Helps to check the status of the insert process
     * @param <T>
     */
    public interface Action<T> {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);
    }
    private static DatabaseReference DriveRef;
    //static List<Drive> driveList;
    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance(); //return a FirebaseDatabase instance.
        DriveRef = database.getReference("Drives");
       // driveList = new ArrayList<>();
    }

    /**
     *Add the new Drive to FireBase
     * @param newDrive Drive a new drive to add
     * @param action Action<String> include 3 functions: onSuccess, onProgress, onFailure
     * @throws Exception .
     */
    @Override
    public void addNewDrive(final Drive newDrive, final Action<String> action) throws Exception {
        addDriveToFirebase(newDrive, action);

    }

    /**
     *
     * @param drive Drive a new drive to add
     * @param action Action<String> include 3 functions: onSuccess, onProgress, onFailure
     */
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
