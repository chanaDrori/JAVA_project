package com.project5779.gettaxi.model.backend;
import android.content.ContentValues;

import com.project5779.gettaxi.model.datasource.DatabaseFirebase;

public interface DBmanager {

    public void addNewDrive(ContentValues contentValues) throws Exception;
}
