package com.project5779.gettaxi.model.backend;
import android.content.ContentValues;

import com.project5779.gettaxi.model.datasource.DatabaseFirebase;
import com.project5779.gettaxi.model.entities.Drive;

public interface DBmanager {
    public void addNewDrive(Drive newDrive, final DatabaseFirebase.Action<String> action) throws Exception;
}
