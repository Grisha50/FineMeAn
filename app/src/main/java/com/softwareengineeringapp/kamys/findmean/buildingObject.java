package com.softwareengineeringapp.kamys.findmean;

/**
 * Created by kamys on 11/2/2016.
 */

public class buildingObject {
    String bathroom;
    String handiCap;
    String elevator;
    String building ;
    String study;
    String info ;
    String lat;
    String longi;
    String Link;


    buildingObject(String Name, String hand, String bath, String elev, String study, String lat, String longi, String link){
        this.bathroom = bath;
        this.building  = Name;
        this.handiCap = hand;
        this.elevator = elev;
        this.longi = longi;
        this.lat = lat;
        this.Link = link;
        this.study = study;
    }

    String BuildingName(){
        return building;
    }
    boolean getBath(){
        if (bathroom == "y")
        {
            return true;
        }
        return false;
    }
    boolean getHand(){
        if (handiCap == "y")
        {
            return true;
        }
        return false;
    }
    boolean getStudy() {
        if (study == "y") {
            return true;
        }
        return false;
    }

    boolean getElev(){
        if (elevator == "y")
        {
            return true;
        }
        return false;
    }

    double longitude(){
        double value = Double.parseDouble(longi);
        return value;
    }
    double latitude(){
        double value = Double.parseDouble(lat);
        return value;
    }
    String getInfo(){
        if ( bathroom == "y"){
            info += "Bathroom, ";
        }
        if( handiCap == "y"){
            info += "Handicapable, " ;
        }
        if (elevator == "y"){
            info +=" Elevator";
        }
        return info ;
    }
}
