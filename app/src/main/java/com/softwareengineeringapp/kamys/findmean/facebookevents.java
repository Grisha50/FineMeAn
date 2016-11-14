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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Andy Ni on 11/3/2016.
 */

public class facebookevents extends Activity {

    List<JSONObject> idList = new ArrayList<JSONObject>();
    GraphRequest idListRequest;
    GraphRequest eventRequest;
    public List<JSONObject> eventIDFinder(String zipcode) {
        idListRequest = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(), "/search",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject jobj = response.getJSONObject();
                        JSONArray jarray = null;
                        try {
                            jarray = jobj.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject events = jarray.getJSONObject(i);
                                idList.add(events);
                            }
                            Iterator<JSONObject> iter = idList.iterator();
                            JSONObject temp = new JSONObject();
                            long currTime = System.currentTimeMillis();
                            System.out.println(currTime);
                            while (iter.hasNext()) {
                                temp = iter.next();
                                String start = temp.getString("start_time");
                                String end = temp.getString("end_time");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
                                long startTime = dateFormat.parse(start).getTime();
                                long endTime = dateFormat.parse(end).getTime();
                                if (startTime - currTime < 86400 && endTime - currTime > 0){
                                    System.out.println(startTime - currTime);
                                    System.out.println(endTime - currTime);
                                    System.out.println("Unix timestamp: " + startTime);
                                } else {
                                    System.out.println(temp.getString("id") + " was removed");
                                    iter.remove();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("q", zipcode);
        parameters.putString("type", "event");
        parameters.putString("fields", "id, name, description, place, start_time, end_time");
        System.out.println(parameters);
        idListRequest.setParameters(parameters);
        idListRequest.executeAsync();

        return this.idList;
    }
}
