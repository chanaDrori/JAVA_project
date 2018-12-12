package com.project5779.gettaxi.model.datasource;

import android.content.ContentValues;

import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;
import com.project5779.gettaxi.model.entities.StateOfDrive;

import java.util.ArrayList;

public class DatabaseList// implements DBmanager
{
    private ArrayList<Drive> DriveList = new ArrayList<Drive>();

    /**
     * The function add new drive to DB list.
     * @param newDrive - Drive  a new drive item to add to ArrayList<Drive> DriveList.
     * @throws Exception if the new object was exist in the list
     */
    //@Override
    public void addNewDrive(Drive newDrive) throws Exception
    {
        for (Drive driveItem : DriveList)
            if (driveItem.equals(newDrive))
                throw new Exception("This drive already exists in the system");
        DriveList.add(newDrive);
    }

}
