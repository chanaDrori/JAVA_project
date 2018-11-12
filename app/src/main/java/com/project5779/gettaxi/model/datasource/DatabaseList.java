package com.project5779.gettaxi.model.datasource;

import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.entities.Drive;

import java.util.ArrayList;

public class DatabaseList implements DBmanager
{
    private ArrayList<Drive> DriveList = new ArrayList<Drive>();

    /**
     *
     * @param newDrive Drive new item to add to ArrayList<Drive> DriveList.
     * @throws Exception if the new object was exist in the list
     */
    @Override
    public void addNewDrive(Drive newDrive) throws Exception
    {
        for (Drive driveItem : DriveList)
            if (driveItem.equals(newDrive))
                throw new Exception("נסיעה זו כבר קיימת במערכת");
        DriveList.add(newDrive);
    }
}
