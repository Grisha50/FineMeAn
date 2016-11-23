package com.softwareengineeringapp.kamys.findmean;

/**
 * Created by kamys on 11/2/2016.
 */

public class buildingObject {
    boolean bathroom = false ;
    boolean handiCap = false ;
    boolean drink = false ;
    boolean elevator = false ;
    String building ;
    String info ;
    double Long;
    double Lat ;


    buildingObject(boolean bath, boolean hand, double Long, double lat, boolean drink , boolean elev , String Name){
     this.bathroom = bath ;
        this.building  = Name ;
        this.handiCap = hand ;
        this.elevator = elev;
        this.drink = drink ;
        this.Long = Long;
        this.Lat = lat ;
    }

    String BuildingName(){
        return building;
    }
    boolean getBath(){
        return bathroom;
    }
    boolean getHand(){
        return handiCap;
    }
    boolean getDrink(){
        return drink;
    }
    boolean getElev(){
        return elevator ;
    }

    double longitude(){
        return Long ;
    }
    double latitude(){
        return Lat ;
    }
    String getInfo(){
        if ( bathroom == true){
            info += "Bathroom, ";
        }
        if( handiCap == true){
           info += "Handicapable, " ;
        }
        if( drink == true){
            info += "Drinking Fountain, ";
        }
        if (elevator == true){
            info +=" Elevator";
        }

        return info ;
    }
}
