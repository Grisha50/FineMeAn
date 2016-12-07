package com.softwareengineeringapp.kamys.findmean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    public static ArrayList<buildingObject> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_final);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        filter = (Button) findViewById(R.id.button2);
        settings = (Button) findViewById(R.id.button3);
        refresh = (Button) findViewById(R.id.button4);
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(43.070500,
                        -89.398364));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        //mMap.setInfoWindowAdapter(new infoWindowAdapter() );

        DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);
        try
        {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try
        {
            //database is opened and read only atm.... Let's parse it for our values to enter
            SQLiteDatabase myDb = myDbHelper.openDataBase();
            String temp;

            //Latitudes
            Cursor crs1 = myDb.rawQuery("SELECT LAT FROM AMENITIES", null);
            List<String> Lat = new ArrayList<String>();
            while(crs1.moveToNext())
            {
                temp = crs1.getString(crs1.getColumnIndex("NAME"));
                Lat.add(temp);
            }

            //Longitudes
            Cursor crs2 = myDb.rawQuery("SELECT LONG FROM AMENITIES", null);
            List<String> Long = new ArrayList<String>();
            while(crs2.moveToNext())
            {
                temp = crs2.getString(crs2.getColumnIndex("NAME"));
                Long.add(temp);
            }

            //Names
            Cursor crs3 = myDb.rawQuery("SELECT BUILDINGNAME FROM AMENITIES", null);
            List<String> BuildingName = new ArrayList<String>();
            while(crs2.moveToNext())
            {
                temp = crs2.getString(crs2.getColumnIndex("NAME"));
                BuildingName.add(temp);
            }

            for (int i = 0; i < BuildingName.size(); i++)
            {
                LatLng Adr = new LatLng(new Double(Lat.get(i)), new Double(Long.get(i)));
                Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title(BuildingName.get(i)));
            }
        }
        catch(SQLException sqle)
        {
            throw sqle;
        }

        LatLng Adr = new LatLng(43.070500, -89.398364);
        Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title("Van Hise"));
        }
    }

