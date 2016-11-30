package com.softwareengineeringapp.kamys.findmean;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andy Ni on 11/3/2016.
 *
 * How to use:
 * Create a FacebookEventSearch object, then call eventFinder, providing it:
 * an int zipcode, an integer to filter events by hours from now, and a boolean
 * for whether the user has given permission to access his personal events.
 */

public class FacebookEventSearch {

    List<JSONObject> EventList = new ArrayList<JSONObject>();
    List<JSONObject> TempList = new ArrayList<JSONObject>();
    GraphRequest PublicEventRequest;
    GraphRequest PrivateEventRequest;

    public List<JSONObject> eventFinder(int zipcode, int timeframe, boolean permission) {
        final int ZipCode = zipcode;
        final int TimeFrame = timeframe;
        final boolean Permission = permission;


        PublicEventRequest = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(), "/search",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject jobj = response.getJSONObject();
                        System.out.println("object =" + jobj);
                        System.out.println("response =" + response);
                        JSONArray jarray = null;
                        try {
                            jarray = jobj.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject events = jarray.getJSONObject(i);
                                System.out.println(events);
                                EventList.add(events);
                            }
                            Iterator<JSONObject> iter = EventList.iterator();
                            JSONObject temp = new JSONObject();
                            long currTime = System.currentTimeMillis();
                            while (iter.hasNext()) {
                                temp = iter.next();
                                String start = temp.getString("start_time");
                                String end = temp.getString("end_time");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
                                long startTime = dateFormat.parse(start).getTime();
                                long endTime = dateFormat.parse(end).getTime();
                                if (startTime - currTime < (3600 * TimeFrame) && endTime - currTime > 0){
                                    //System.out.println(startTime - currTime);
                                    //System.out.println(endTime - currTime);
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
                        }catch (NullPointerException e){

                        }
                    }
                });



        PrivateEventRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        JSONObject jobj = response.getJSONObject();
                        System.out.println("object =" + jobj);
                        //System.out.println("response =" + response);
                        JSONArray jarray = null;
                        try {
                            jarray = jobj.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject events = jarray.getJSONObject(i);
                                TempList.add(events);
                            }
                            Iterator<JSONObject> iter = TempList.iterator();
                            JSONObject temp = new JSONObject();
                            long currTime = System.currentTimeMillis();
                            while (iter.hasNext()) {
                                temp = iter.next();
                                String start = temp.getString("start_time");
                                String end = temp.getString("end_time");
                                SimpleDateFormat dateFormat =
                                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
                                long startTime = dateFormat.parse(start).getTime();
                                long endTime = dateFormat.parse(end).getTime();
                                if (startTime - currTime < (3600 * TimeFrame) && endTime - currTime > 0){
                                    //System.out.println(startTime - currTime);
                                    //System.out.println(endTime - currTime);
                                    System.out.println("Unix timestamp: " + startTime);
                                } else {
                                    //System.out.println(temp.getString("id") + " was removed");
                                    iter.remove();
                                }
                            }
                            EventList.removeAll(TempList);
                            EventList.addAll(TempList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("q", Integer.toString(ZipCode));
        parameters.putString("type", "event");
        parameters.putString("fields", "id, name, description, place, start_time, end_time");
        System.out.println(parameters);
        PublicEventRequest.setParameters(parameters);
        PublicEventRequest.executeAsync();
        if (Permission == true) {
            Bundle parameters2 = new Bundle();
            parameters2.putString("fields", "events");
            PrivateEventRequest.setParameters(parameters2);
            PrivateEventRequest.executeAsync();
        }

        return this.EventList;
    }
}