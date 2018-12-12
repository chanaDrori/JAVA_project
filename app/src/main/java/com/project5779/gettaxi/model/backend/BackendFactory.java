package com.project5779.gettaxi.model.backend;

import android.content.Context;

public final class BackendFactory
{
    public static String mode = "fire";
    public static DBmanager instance = null;
    public static final DBmanager getInstance(Context context)
    {
        if (mode.equals("list")) {
           /* if (instance == null) {
                  instance = new com.project5779.gettaxi.model.datasource.DatabaseList();
            }*/
            return instance;
        }
        else
        {
            if (instance == null) {
                 instance = new com.project5779.gettaxi.model.datasource.DatabaseFirebase();
            }
            return instance;
        }
    }

}
