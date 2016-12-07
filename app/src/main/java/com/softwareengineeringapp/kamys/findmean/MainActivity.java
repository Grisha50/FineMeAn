package com.softwareengineeringapp.kamys.findmean;

import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
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

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

         // Intialize facebook sdk
        AppEventsLogger.activateApp(getApplication());

        //uncomment when running test:

        //try {
        //    FacebookEventSearchTest();
        //}catch(Exception e){
        //    Log.i("FacebookEventSearch", "ERROR: Exception thrown by FacebookEventSearchTest");
        //}

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
            public void onCancel() {info.setText("Login attempt cancelled.");}

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


}
