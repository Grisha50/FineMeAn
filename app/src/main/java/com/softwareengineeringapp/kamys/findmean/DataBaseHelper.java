package com.softwareengineeringapp.kamys.findmean;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DataBaseHelper extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "Buildings.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}