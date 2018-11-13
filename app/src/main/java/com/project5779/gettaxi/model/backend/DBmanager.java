package com.project5779.gettaxi.model.backend;
import android.content.ContentValues;

import com.project5779.gettaxi.model.entities.Drive;
import java.util.*;

public interface DBmanager {

    public void addNewDrive(ContentValues contentValues) throws Exception;
}
