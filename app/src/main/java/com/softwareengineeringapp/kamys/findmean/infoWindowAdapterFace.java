package com.softwareengineeringapp.kamys.findmean;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class infoWindowAdapterFace implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView eventName;
    private TextView time;
    private TextView description;
    private static int index;


    public infoWindowAdapterFace(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.facebook_building_window, null);
        if (marker != null) {
            facebookObject b = MapsActivity.mEventList.get(index);
            eventName = (TextView) v.findViewById(R.id.eventName);
            eventName.setText(marker.getTitle());
            time = (TextView) v.findViewById(R.id.eventTime);
            time.setText(b.date);
            //description = (TextView) v.findViewById(R.id.eventDescription);
            //description.setText(b.description);
            index++;
        }
        index = (index == MapsActivity.mEventList.size()-1)?0:index++;
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}
