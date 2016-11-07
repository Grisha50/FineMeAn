package com.softwareengineeringapp.kamys.findmean;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Andy Ni on 11/3/2016.
 */

public class facebookevents extends Activity {

    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.textView);
        output.setText("here");
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        output = (TextView) findViewById(R.id.textView);
                        JSONArray obj = response.getJSONArray();
  //                      output.setText(obj.toString());
                        System.out.println(response);
                        System.out.println(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, place, start_time, end_time");
        parameters.putString("q", "53703");
        parameters.putString("type", "event");
        request.setParameters(parameters);
        request.executeAsync();
    }




}
