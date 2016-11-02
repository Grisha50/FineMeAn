package com.softwareengineeringapp.kamys.findmean;

import java.sql.Time;

/**
 * Created by kamys on 11/2/2016.
 */

public class facebookObject {
    String eventName ;
    String time ;
    String date ;
    String Address ;

    facebookObject(String Name, String time , String date , String Address){

        this.eventName = Name ;
        this.time = time ;
        this.date = date ;
        this.Address = Address;
    }

    String getEventName(){
        return  eventName ;
    }
    String getTime(){
        return time ;
        }
    String getDate(){
        return date ;
    }
    String getAddress(){
        return Address ;
    }
}
