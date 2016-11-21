package com.softwareengineeringapp.kamys.findmean;

/**
 * Used to store values for individual Buildings
 */
public class buildingObject {
    int bathroom = 0;
    int handiCap = 0;
    //int drink = 0;
    int elevator = 0;
    int study = 0;

    String building;
    double longi;
    double lat;
    String info; //used to convert to Google Maps LatLng

    /**
     * Represents the Database Schema for the Building; params are self explanatory for now...
     * @param name building
     * @param hand handicap
     * @param bath bathroom
     * @param elev elevator
     * @param study study area
     * @param longi longitude
     * @param lat latitude
     */
    buildingObject(String name, int hand, int bath, int elev, int study, double longi, double lat)
    {
        this.bathroom = bath;
        this.building  = name;
        this.handiCap = hand;
        this.elevator = elev;
        //this.drink = drink;
        this.study = study;
        this.longi = longi;
        this.lat = lat;
    }

    /**
     * Getter for the name
     * @return string
     */
    String BuildingName()
    {
        return building;
    }

    /**
     * Getter for handicap or not
     * @return int
     */
    int getHand()
    {
        return handiCap;
    }

    /**
     * Getter for bathrooms
     * @return int
     */
    int getBath()
    {
        return bathroom;
    }

    /** NOT USING THIS METHOD THIS ITERATION
     int getDrink()
    {
        return drink;
    }
    */

    /**
     * Getter for Elevators
     * @return int
     */
    int getElev()
    {
        return elevator ;
    }

    /**
     * Getter for Study Areas
     * @return int
     */
    int getStudy()
    {
        return study;
    }

    /**
     * Getter for Logitude
     * @return double
     */
    double getLong()
    {
        return longi ;
    }

    /**
     * Getter for Latitude
     * @return double
     */
    double getLat()
    {
        return lat ;
    }

    /**
     * Getter for the Google Maps LatLng portion (called from MapsActivity)
     * @return string
     */
    String getInfo(){
        if ( bathroom == 1)
        {
            info += "Bathroom, ";
        }

        if( handiCap == 1)
        {
            info += "Handicapable, " ;
        }

        /**
        if( drink == 1)
        {
            info += "Drinking Fountain, ";
        }
        */

        if (elevator == 1)
        {
            info +=" Elevator";
        }

        return info ;
    }


}
