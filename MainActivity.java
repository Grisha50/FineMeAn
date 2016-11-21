package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {
    DatabaseHelper myDb;
    private TextView info;
    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Database (Add currently known Buildings)
        //SCHEMA: buildingObject(String name, int hand, int bath, int elev, int study, double longi, double lat)
        myDb = new DatabaseHelper(this); //constructs an instance of the database
        myDb.addBuilding(new buildingObject("Van Hise", 1, 1, 1, 1, 43.075541, -89.406711));
        myDb.addBuilding(new buildingObject("Sterling", 0, 1, 1, 1, 43.074616, -89.405389));
        myDb.addBuilding(new buildingObject("Chamberlin", 0, 1, 1, 1, 43.073944, -89.405479));
        myDb.addBuilding(new buildingObject("Van Vleck", 0, 1, 1, 1, 43.074695, -89.404448));
        myDb.addBuilding(new buildingObject("Gordon", 0, 1, 1, 1, 43.071656, -89.398192)); //TODO
        myDb.addBuilding(new buildingObject("SERF", 1, 1, 1, 1, 43.070500, -89.398364));
        /**
         * SCHEMA FOR FURTHER BUILDING ADDITIONS:
         * SCHEMA: buildingObject(String name, int hand, int bath, int elev, int study, double longi, double lat)
         * myDb.addBuilding(new buildingObject("", 1, 1, 1, 1, , ));
         */


         // Intialize facebook sdk
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);


        // Gets key hash value off your machine for facebook authentication.
        //DO NOT TOUCH THIS TRY CATCH STATEMENTs


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                        String userID = loginResult.getAccessToken().getUserId() ;
                        String userToken  = loginResult.getAccessToken().getToken();

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void guestLogin(View view)
    {
        Intent intent = new Intent(this,IntermediateMap.class );
        startActivity(intent);
    }
}
