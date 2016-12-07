package com.softwareengineeringapp.kamys.findmean;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    private ViewGroup group;
    public static ArrayList<buildingObject> buildings;
    private Button filter;
    private Button settings;
    private Button refresh;

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
        List<Marker> theMarkers = new ArrayList<>();
        mMap.setInfoWindowAdapter(new infoWindowAdapter() );
        LatLng Adr = new LatLng(43.070500, -89.398364);
        Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(Adr)
                        .title("Van Hise")
                        );
                theMarkers.add(marker);

        }
    }

