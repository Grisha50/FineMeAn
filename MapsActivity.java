package com.softwareengineeringapp.kamys.findmean;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    public static ArrayList<buildingObject> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        List<Marker> theMarkers = new ArrayList<>();
        mMap.setInfoWindowAdapter(new infoWindowAdapter() );
        int counter = 0 ;
        if(buildings != null) {
            while (counter < buildings.size()) {
                LatLng Adr = new LatLng(buildings.get(counter).lat, buildings.get(counter).longi);
                Marker marker = mMap.addMarker(new MarkerOptions().position(Adr).
                        title(buildings.get(counter).BuildingName()).snippet(buildings.get(counter).getInfo()).icon(BitmapDescriptorFactory.fromResource(R.layout.building_info_window)));
                theMarkers.add(marker);
            }
        }

        

        // Add a marker in Sydney and move the camera

    }
}
