/**
 * Project in Java- Android.
 * Writers - Tirtza Raaya Rubinstain && Chana Drori
 * 12/2018
 * the class BackendFactory return instance of backend.
 */

package com.project5779.gettaxi.model.backend;

import android.content.Context;

import com.project5779.gettaxi.model.datasource.DatabaseFirebase;
import com.project5779.gettaxi.model.datasource.DatabaseList;

public final class BackendFactory
{
    private static String mode = "fire";
    private static BackEnd instance = null;

    /**
     * the function return instance of backend.
     * @param context Context of the function
     * @return backend.
     */
    public static final BackEnd getInstance(Context context)
    {
        if (mode.equals("list")) {
            /*if (instance == null) {
                  instance = new DatabaseList();
            }*/
            return instance;
        }
        else
        {
            if (instance == null) {
                 instance = new DatabaseFirebase();
            }
            return instance;
        }
    }

}
