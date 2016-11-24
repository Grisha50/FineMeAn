package com.softwareengineeringapp.kamys.findmean;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by kamys on 10/26/2016.
 */

public class facebookInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView eventName;
    private TextView eventAddress;
    private TextView eventTime;
    private TextView eventDate;

    public facebookInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.building_info_window, null);
        if (marker != null) {
            eventName = (TextView) v.findViewById(R.id.bName);
            eventName.setText(marker.getTitle());
            eventTime = (TextView) v.findViewById(R.id.bList);
            eventTime.setText(marker.getTitle());
            eventDate = (TextView) v.findViewById(R.id.bList);
            eventDate.setText(marker.getTitle());
            eventAddress = (TextView) v.findViewById(R.id.bList);
            eventAddress.setText(marker.getTitle());
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}
