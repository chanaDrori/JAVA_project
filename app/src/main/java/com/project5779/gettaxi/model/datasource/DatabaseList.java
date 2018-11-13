package com.project5779.gettaxi.model.datasource;

import android.content.ContentValues;

import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;
import com.project5779.gettaxi.model.entities.StateOfDrive;

import java.util.ArrayList;

public class DatabaseList implements DBmanager
{
    private ArrayList<Drive> DriveList = new ArrayList<Drive>();

    /**
     * The function add new drive to DB list.
     * @param contentValues ContentValues new drive item to add to ArrayList<Drive> DriveList.
     * @throws Exception if the new object was exist in the list
     */
    @Override
    public void addNewDrive(ContentValues contentValues) throws Exception
    {
        Drive newDrive = ContentValuesToDrive(contentValues);
        for (Drive driveItem : DriveList)
            if (driveItem.equals(newDrive))
                throw new Exception("נסיעה זו כבר קיימת במערכת");
        DriveList.add(newDrive);
    }

    /**
     * The function convert ContentValues to Drive
     * @param contentValues ContentValues contain values for a drive
     * @return Drive from the contentValues
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
