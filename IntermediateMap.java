package com.softwareengineeringapp.kamys.findmean;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by kamys on 10/10/2016.
 */

public class IntermediateMap extends Activity {
    Fragment mapFrag ;
    public static ArrayList<buildingObject> buildings ;
    public static Context context;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.blank_layout);
        context = this.getApplicationContext();

        intent = new Intent(this,MapsActivity.class);
        startActivity(intent);





    }


}
