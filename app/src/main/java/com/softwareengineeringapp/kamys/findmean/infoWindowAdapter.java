package com.softwareengineeringapp.kamys.findmean;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Marker;

public class infoWindowAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView buildingName;
    private TextView bathroom;
    private TextView handicap;
    private TextView elevators;
    private TextView StudyArea;
    private static int index;


    public infoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.building_window, null );
        if (marker != null) {
            buildingObject b = MapsActivity.buildings.get(index);
            buildingName = (TextView) v.findViewById(R.id.bname);
            buildingName.setText(marker.getTitle());
            bathroom = (TextView) v.findViewById(R.id.Bathroom);
            bathroom.setText(b.bathroom.equals("y")?"Bathrooms: Open to the public and accessible.":"Funny thing, no bathrooms here!");
            handicap = (TextView) v.findViewById(R.id.Ramps);
            handicap.setText(b.handiCap.equals("y")?marker.getTitle() + " is accessible from the ground floor, with a ramp where necessary":
                    "This building is not easily accessible for people with limited mobility or wheelchairs");
            elevators=(TextView) v.findViewById(R.id.Elevators);
            elevators.setText(b.elevator.equals("y")?"Elevators are available.":"Elevators are not available.");
            StudyArea = (TextView) v.findViewById(R.id.studyarea);
            StudyArea.setText(b.study.equals("y")?"There is a (possibly unofficial) study area" +
                    " available on the main floor.":"No study areas available.");
            index++;
        }
        index = (index == MapsActivity.buildings.size()-1)?0:index++;
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}
