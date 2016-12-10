package com.softwareengineeringapp.kamys.findmean;

/**
 * Created by kamys on 11/2/2016.
 */

public class buildingObject {
    String bathroom;
    String handiCap;
    String drink;
    String elevator;
    String building ;
    String info ;
    String lat;
    String longi;
    String Link;


    buildingObject(String Name, String hand, String bath, String elev, String study, String lat, String longi, String link){
        this.bathroom = bath;
        this.building  = Name;
        this.handiCap = hand;
        this.elevator = elev;
        this.drink = drink;
        this.longi = longi;
        this.lat = lat;
        this.Link = link;
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
    boolean getDrink(){
        if (drink == "y")
        {
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
        if( drink == "y"){
            info += "Drinking Fountain, ";
        }
        if (elevator == "y"){
            info +=" Elevator";
        }
        return info ;
    }
}
