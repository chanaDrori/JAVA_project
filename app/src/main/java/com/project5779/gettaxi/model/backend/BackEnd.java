/**
 * Project in Java- Android.
 * Writers - Tirtza Raaya Rubinstain && Chana Drori
 * 12/2018
 * the interface add the new drive.
 */

package com.project5779.gettaxi.model.backend;
import android.content.ContentValues;

import com.project5779.gettaxi.model.datasource.DatabaseFirebase;
import com.project5779.gettaxi.model.entities.Drive;

/**
 *the interface add the new drive.
 */
public interface BackEnd {
    public void addNewDrive(Drive newDrive, final DatabaseFirebase.Action<String> action) throws Exception;
}
