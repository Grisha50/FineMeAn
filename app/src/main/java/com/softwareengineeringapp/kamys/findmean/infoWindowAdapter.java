package com.softwareengineeringapp.kamys.findmean;

import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class infoWindowAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView buildingName;
    private TextView buildingList ;

    public infoWindowAdapter() {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.building_info_window, null);
        if (marker != null) {
            buildingName = (TextView) v.findViewById(R.id.bName);
            buildingName.setText(marker.getTitle());
            buildingList = (TextView) v.findViewById(R.id.bList);
            buildingList. setText(marker.getTitle());
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}
