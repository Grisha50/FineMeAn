package com.softwareengineeringapp.kamys.findmean;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "Buildings.db";

    // Amenities Table name
    private static final String TABLE_NAME = "Amenities";

    // Table Columns names
    public static final String COL1 = "BUILDING_NAME";
    public static final String COL2 = "HAND";
    public static final String COL3 = "BATHROOMS";
    public static final String COL4 = "ELEVATORS";
    public static final String COL5 = "STUDY_AREA";
    public static final String COL6 = "LONG";
    public static final String COL7 = "LAT";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AMENITIES_TABLE = "CREATE TABLE " + DATABASE_NAME + "("
                + COL1 + " TEXT PRIMARY KEY," //Building Name
                + COL2 + " TEXT,"             //Handicap
                + COL3 + " TEXT,"             //Bathroom
                + COL4 + " TEXT,"             //Elevators
                + COL5 + " TEXT,"             //Study Area
                + COL6 + " TEXT,"             //Long
                + COL7 + " TEXT,"             //Lat
                + ")";
        db.execSQL(CREATE_AMENITIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Creating tables again
        onCreate(db);
    }

    // Adding new Building
    public void addBuilding(buildingObject building) {
        SQLiteDatabase db = this.getWritableDatabase();

        //SCHEMA: buildingObject(String name, int hand, int bath, int elev, Double long, Double lat)

        ContentValues values = new ContentValues();
        values.put(COL1, building.BuildingName()); //Building Name
        values.put(COL2, building.getHand()); //HandiCap
        values.put(COL3, building.getBath()); //Bathroom
        values.put(COL4, building.getElev()); //Elevators
        //values.put(COL5, building.getStudy()); //Study area
        values.put(COL6, building.longitude()); //Longitude
        values.put(COL7, building.latitude()); //Latitude

        // Inserting a new Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
}