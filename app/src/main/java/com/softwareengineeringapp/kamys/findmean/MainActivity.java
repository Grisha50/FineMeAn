package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

public class MainActivity extends Activity {
    private TextView info;
    private LoginButton loginButton;
    private Button guestButton;
    private CallbackManager callbackManager;

    public static SharedPreferences settings;
    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

         // Intialize facebook sdk
        AppEventsLogger.activateApp(getApplication());

//        try {
//            FacebookEventSearchTest();
//       }catch(Exception e){
//            Log.i("FacebookEventSearch", "ERROR: Exception thrown by FacebookEventSearchTest");
//        }

        callbackManager = CallbackManager.Factory.create();

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        guestButton = (Button)findViewById(R.id.guestLogin);


        // Gets key hash value off your machine for facebook authentication.
        //DO NOT TOUCH THIS TRY CATCH STATEMENTs

        prefInit();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                String userID = loginResult.getAccessToken().getUserId() ;
                String userToken  = loginResult.getAccessToken().getToken();

                mapView();
                //finish();
            }

            @Override
            public void onCancel() {info.setText("Login attempt cancelled.");}

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });


        guestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                mapView();
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void mapView() {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void FacebookEventSearchTest() throws Exception{
        //Testing FacebookEventSearch
        FacebookEventSearch searcher = new FacebookEventSearch();
        List<JSONObject> mEventList = searcher.eventFinder(53706, 24, false);

        assertNotNull("ERROR: Event Search returned null pointer", mEventList);
        if(mEventList.isEmpty()){
            return;
        }
        for(JSONObject o:mEventList){
            assertNotNull("ERROR: Null JSONObject in Event List", o);
            assertFalse("ERROR: Null fields in JSONObjects",
                    o.get("start_time") == null &&
                            o.get("end_time") == null &&
                            o.get("name") == null &&
                            o.get("description") == null &&
                            o.get("place") == null);
        }
    }

    public void prefInit()
    {
        settings = getSharedPreferences(getString(R.string.MYPREFS), 0);
    }

    public void editPref(String key, int value)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPref(String key)
    {
        return settings.getInt(key, 0);
    }


}
