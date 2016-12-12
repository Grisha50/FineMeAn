package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;

import static android.R.attr.data;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static MapsActivity instance = null;
    public static ArrayList<String> buildingids = new ArrayList<>();
    public static HashMap<Marker, facebookObject> facebookMap = new HashMap<>();
    private static boolean firstRun = true;
    public static ArrayList<buildingObject> filteredList = new ArrayList<buildingObject>();
    private GoogleMap mMap;
    private Button filter;
    private Button settings;
    private Button refresh;
    private ArrayList<buildingObject> mainList = new ArrayList<buildingObject>();
    private SearchView searchView;

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
                startActivityForResult(new Intent(MapsActivity.this, FilterWindow.class), 1);
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
                mMap.clear();
                createPins(filteredList);
                if (AccessToken.getCurrentAccessToken() != null) {
                    searcher(53706, 100, false);
                }
            }
        });

        searchView = (SearchView) findViewById(R.id.searchbar);
        searchView.setQueryHint("Search View");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String comparison = query.toLowerCase().trim();
                String building;
                //boolean found = false;
                for(buildingObject  b : mainList){
                    building = b.BuildingName().toLowerCase().trim();
                    Log.d(comparison, building);
                    if(building.equals(comparison)){
                        LatLng Coord = new LatLng(b.latitude(),b.longitude()) ;
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Coord,17));
                        mMap.addMarker(new MarkerOptions()
                                .title(b.building)
                                .position(Coord));
                        return true;
                    }

                }
                //if( found == false){
                //Toast.makeText(getBaseContext(),"Building Not Found", Toast.LENGTH_LONG);
                //}
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SetPins("x", "x", "x", "x");
    }

    public void createPins(ArrayList<buildingObject> pinList){
        int items=pinList.size();
        Location userLoc = new Location("");
        Location buildingLoc = new Location("");
        //TODO: Pull actual location
        userLoc.setLatitude(43.071486);
        userLoc.setLongitude(-89.406630);
        for (int i=0; i<items; i++){
            double lat = Double.parseDouble(pinList.get(i).lat);
            double longi = Double.parseDouble(pinList.get(i).longi);
            buildingLoc.setLatitude(lat);
            buildingLoc.setLongitude(longi);
            if(userLoc.distanceTo(buildingLoc)/1000 <= ((double)MainActivity.instance.getPref(getString(R.string.DRAWDIST)) / 4)) {
                LatLng Adr = new LatLng(lat, longi);
                Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).title(pinList.get(i).building));
                buildingids.add(marker.getId());
            }
        }
    }

    public void createEventPins(List<facebookObject> eventList) {
        Location userLoc = new Location("");
        Location eventLoc = new Location("");
        //TODO: Pull actual location
        userLoc.setLatitude(43.071486);
        userLoc.setLongitude(-89.406630);
        if (AccessToken.getCurrentAccessToken() != null) {
            int items = eventList.size();
            for (int i = 0; i < items; i++) {
                double lat = Double.parseDouble(eventList.get(i).getLatitude());
                double longi = Double.parseDouble(eventList.get(i).getLongitude());
                eventLoc.setLatitude(lat);
                eventLoc.setLongitude(longi);
                if(userLoc.distanceTo(eventLoc)/1000 <= ((double)MainActivity.instance.getPref(getString(R.string.DRAWDIST)) / 4)) {
                    LatLng Adr = new LatLng(lat, longi);
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(Adr)
                            .title(eventList.get(i).eventName)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    facebookMap.put(marker, eventList.get(i));
                }
            }
        }
    }


    public void SetPins(String restroom, String elevator, String handicap, String studyArea)
    {
        DataBaseHelper myDb = new DataBaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        filteredList.clear();
        Cursor cur = null;

        if (firstRun)
        {
            cur = db.rawQuery("SELECT * FROM Amenities", null);
            Log.d("FIRST RUN", "TRUE!");
        }else{
            String []args = new String[0];
            String query = "SELECT * FROM Amenities WHERE ";
            if(restroom.equals("x")){
                //query += "Bathrooms = * AND ";
            }else{
                query += "Bathrooms = ? AND ";
                String []tmp = args;
                args = new String[args.length+1];
                int i = 0;
                for(String s:tmp){
                    args[i] = tmp[i];
                    i++;
                }
                args[i] = restroom;
            }
            if(elevator.equals("x")){
                //query += "Elevators = * AND ";
            }else{
                query += "Elevators = ? AND ";
                String []tmp = args;
                args = new String[args.length+1];
                int i = 0;
                for(String s:tmp){
                    args[i] = tmp[i];
                    i++;
                }
                args[i] = elevator;
            }
            if(handicap.equals("x")){
                //query += "Hand = * AND ";
            }else{
                query += "Hand = ? AND ";
                String []tmp = args;
                args = new String[args.length+1];
                int i = 0;
                for(String s:tmp){
                    args[i] = tmp[i];
                    i++;
                }
                args[i] = handicap;
            }
            if(studyArea.equals("x")){
                query = query.substring(0, query.length() - 4);
            }else{
                query += "StudyArea = ? ";
                String []tmp = args;
                args = new String[args.length+1];
                int i = 0;
                for(String s:tmp){
                    args[i] = s;
                    i++;
                }
                args[i] = studyArea;
            }

            if(restroom.equals("x") && elevator.equals("x") && handicap.equals("x") && studyArea.equals("x")) {
                cur = db.rawQuery("SELECT * FROM Amenities", args);
            }else{
                cur = db.rawQuery(query, args);
            }

        }

        if (cur.moveToFirst())
        {
            while (!cur.isAfterLast())
            {
                String name = cur.getString(cur.getColumnIndex("BuildingName"));
                String hand = cur.getString(cur.getColumnIndex("Hand"));
                String bath = cur.getString(cur.getColumnIndex("Bathrooms"));
                String elev = cur.getString(cur.getColumnIndex("Elevators"));
                String study = cur.getString(cur.getColumnIndex("StudyArea"));
                String lat = cur.getString(cur.getColumnIndex("Lat"));
                String longi = cur.getString(cur.getColumnIndex("Long"));
                String link = cur.getString(cur.getColumnIndex("Link"));

                buildingObject bObject = new buildingObject(name, hand, bath, elev, study, lat, longi, link);

                if (firstRun)
                {
                    mainList.add(bObject);
                    Log.d("FIRST RUN", bObject.building);
                }
                else
                {
                    filteredList.add(bObject);
                }

                cur.moveToNext();
            }
        }
        cur.close();
        db.close();
        firstRun = false;
    }


    public void finishActivity() {

        super.finish();
        instance = null;
    }

    public List<facebookObject> searcher(int zipcode, int time, boolean permissions) {
        FacebookEventSearch search = new FacebookEventSearch();
        return search.eventFinder(zipcode, time, permissions);
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

        createPins(mainList);
        filteredList.clear();
        filteredList.addAll(mainList);
        if (AccessToken.getCurrentAccessToken() != null) {
            searcher(53706, 100, false);
        }
        mMap.setInfoWindowAdapter(new infoWindowAdapter(this.getLayoutInflater()));
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        if (requestCode == 1 && resultCode == 1) {
            String arg[] = FilterWindow.updatedArgs;
            //Log.d("FILTER", "onClick: " + arg[0] + arg[1] + arg[2] + arg[3]);
            SetPins(arg[0], arg[1], arg[2], arg[3]);
            mMap.clear();
            createPins(filteredList);
            if (AccessToken.getCurrentAccessToken() != null && arg[4].equals("y")) {
                searcher(53706, 100, false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
