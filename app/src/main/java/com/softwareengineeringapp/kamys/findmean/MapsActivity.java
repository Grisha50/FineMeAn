package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import static android.R.attr.id;
import static android.R.id.list;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    public static MapsActivity instance = null;
    public static ArrayList<buildingObject> buildings;
    private Button filter;
    private Button settings;
    private Button refresh;
    public List<facebookObject> mEventList;
    ArrayList<buildingObject> mainList = new ArrayList<buildingObject>();
    ArrayList<buildingObject> filteredList = new ArrayList<buildingObject>();
    buildingObject bObject;
    private SearchView searchView;
    boolean firstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_maps_final);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (AccessToken.getCurrentAccessToken() != null) {
            mEventList = searcher(53706, 100, false);
        }
        filter = (Button) findViewById(R.id.button2);
        settings = (Button) findViewById(R.id.button3);
        refresh = (Button) findViewById(R.id.button4);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,FilterWindow.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,SettingsWindow.class));
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventList = searcher(53706, MainActivity.instance.getPref(getString(R.string.TIME)), false);
                mMap.clear();
                createPins(mainList);
                createEventPins(mEventList);
            }
        });
        searchView = (SearchView) findViewById(R.id.searchbar);
        searchView.setQueryHint("Search View");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                  String comparison = query.toLowerCase();
                String building ;
                boolean found = false ;
                 for( buildingObject  b : mainList){
                     building = b.BuildingName().toLowerCase();
                     if(building == comparison){
                         LatLng Cord = new LatLng(b.latitude(),b.longitude()) ;
                         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cord,17));
                         found = true ;
                     }

                 }
                if( found == false){
                    Toast.makeText(getBaseContext(),"Building Not Found", Toast.LENGTH_LONG);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SetPins("y", "y", "y", "y");
    }
    
        public void createPins(ArrayList<buildingObject> pinList){
        int items=pinList.size();
        for (int i=0; i<items; i++){
            double lat = Double.parseDouble(pinList.get(i).lat);
            double longi = Double.parseDouble(pinList.get(i).longi);
            LatLng Adr = new LatLng(lat, longi);
            Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title(pinList.get(i).building));
        }
    }


    public void SetPins(String restroom, String elevator, String handicap, String studyArea)
    {
        DataBaseHelper myDb = new DataBaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        mainList.clear();
        Cursor cur;

        if (firstRun == true)
        {
            cur = db.rawQuery("SELECT * FROM Amenities", null);
        }

        else
        {
            cur = db.rawQuery("SELECT * FROM Amenities " +
                    "WHERE Bathrooms = ? " +
                    "AND Elevators = ? " +
                    "AND Hand = ? " +
                    "AND StudyArea = ?", new String[]{restroom, elevator, handicap, studyArea});
        }

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

                if (firstRun == true)
                {
                    mainList.add(bObject);
                }
                else
                {
                    filteredList.add(bObject);
                }

                cur.moveToNext();
            }
        }
        cur.close();
        firstRun = false;
    }


    public void finishActivity() {

        super.finish();
        instance = null;
    }

    public List<facebookObject> searcher(int zipcode, int time, boolean permissions) {
        FacebookEventSearch search = new FacebookEventSearch();
        mEventList = search.eventFinder(zipcode, time, permissions);
        return mEventList;
    }

    public void createEventPins(List<facebookObject> eventList) {
        if (AccessToken.getCurrentAccessToken() != null) {
            //int items=eventList.size();
            //for (int i=0; i<items; i++){
            //  double lat = Double.parseDouble(eventList.get(i).getLocation());
            //double longi = Double.parseDouble(eventList.get(i).getLocation());
            //LatLng Adr = new LatLng(lat, longi);
            //Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title(eventList.get(i).eventName));
            //markerList.add(marker);
            //}
        }
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

        //Dummy Marker for testing
        //LatLng Adr = new LatLng(43.070500, -89.398364);
        //Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title("Van Hise"));
        createPins(mainList);
        createEventPins(mEventList);
        mMap.setInfoWindowAdapter(new infoWindowAdapter(this.getLayoutInflater()));

        //LatLng Adr = new LatLng(43.070500, -89.398364);
        //Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title("Van Hise"));
    }
}