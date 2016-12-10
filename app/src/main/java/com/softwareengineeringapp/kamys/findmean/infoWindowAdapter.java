package com.softwareengineeringapp.kamys.findmean;

import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.plus.model.people.Person;

public class infoWindowAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView buildingName;
    private TextView bathroom;
    private TextView handicap;
    private TextView elevators;
    private TextView StudyArea;


    public infoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.building_window, null );
        if (marker != null) {
            buildingName = (TextView) v.findViewById(R.id.bname);
            buildingName.setText(marker.getTitle());
            bathroom = (TextView) v.findViewById(R.id.Bathroom);
            bathroom.setText("database");
            handicap = (TextView) v.findViewById(R.id.Ramps);
            handicap.setText("database");
            elevators=(TextView) v.findViewById(R.id.Elevators);
            elevators.setText("database");
            StudyArea = (TextView) v.findViewById(R.id.studyarea);
            StudyArea.setText("database");
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}
