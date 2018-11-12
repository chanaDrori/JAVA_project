package com.project5779.gettaxi.model.backend;

import android.content.Context;

public final class BackendFactory
{
    static DBmanager instance = null;
    public final static DBmanager getInstance(Context context)
    {
        if (instance == null) {
          //  instance = new model.datasource.
        }
        return instance;
    }

}
