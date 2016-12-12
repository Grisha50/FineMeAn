package com.softwareengineeringapp.kamys.findmean;

import java.sql.Time;

/**
 * Created by kamys on 11/2/2016.
 */

public class facebookObject {
    String eventName ;
    String description;
    String ID ;
    String date ;
    String longitude;
    String latitude;

    facebookObject(String Name, String description, String id , String date , String longitude, String latitude){

        this.eventName = Name ;
        this.description = description;
        this.ID = id ;
        this.date = date ;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    String getEventName(){
        return  eventName ;
    }
    String getEventDescription() {
        return description;
    }
    String getID(){
        return ID ;
    }
    String getDate(){
        return date ;
    }
    String getLongitude(){
        return longitude;
    }
    String getLatitude(){
        return latitude;
    }

}