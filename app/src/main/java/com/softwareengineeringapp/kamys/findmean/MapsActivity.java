package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;
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
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static android.R.attr.id;
import static android.R.id.list;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    public static MapsActivity instance = null;
    public static ArrayList<buildingObject> buildings;
    private Button filter;
    private Button settings;
    private Button refresh;
    ArrayList<buildingObject> mainList = new ArrayList<buildingObject>();
    buildingObject bObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_maps_final);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        filter = (Button) findViewById(R.id.button2);
        settings = (Button) findViewById(R.id.button3);
        refresh = (Button) findViewById(R.id.button4);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, FilterWindow.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, SettingsWindow.class));
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void SetPins(String restroom, String elevator, String handicap, String studyArea)
    {
        DataBaseHelper myDb = new DataBaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        mainList.clear();

        Cursor cur = db.rawQuery("SELECT * FROM Amenities " +
                "WHERE Bathrooms = ? " +
                "AND Elevators = ? " +
                "AND Hand = ? " +
                "AND StudyArea", new String[]{restroom, elevator, handicap, studyArea});

        if (cur.moveToFirst())
        {
            while (cur.isAfterLast() == false)
            {
                String name = cur.getString(cur.getColumnIndex("BuildingName"));
                String hand = cur.getString(cur.getColumnIndex("Hand"));
                String bath = cur.getString(cur.getColumnIndex("Bathrooms"));
                String elev = cur.getString(cur.getColumnIndex("Elevators"));
                String study = cur.getString(cur.getColumnIndex("StudyArea"));
                String lat = cur.getString(cur.getColumnIndex("Lat"));
                String longi = cur.getString(cur.getColumnIndex("Long"));
                String link = cur.getString(cur.getColumnIndex("Link"));

                bObject = new buildingObject(name, hand, bath, elev, study, lat, longi, link);
                mainList.add(bObject);

                cur.moveToNext();
            }
        }
    }


    public void finishActivity()
    {
        super.finish();
        instance = null;
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
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(43.070500,
                        -89.398364));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        //mMap.setInfoWindowAdapter(new infoWindowAdapter() );

        LatLng Adr = new LatLng(43.070500, -89.398364);
        Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title("Van Hise"));
    }

}