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
    List<facebookObject> FacebookList = new ArrayList<facebookObject>();
    GraphRequest PublicEventRequest;
    GraphRequest PrivateEventRequest;

    public List<facebookObject> eventFinder(int zipcode, int timeframe, boolean permission) {
        final int ZipCode = zipcode;
        final int TimeFrame = timeframe;
        final boolean Permission = permission;


        PublicEventRequest = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(), "/search",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject jobj = response.getJSONObject();
                        //System.out.println("object =" + jobj);
                        //System.out.println("response =" + response);
                        JSONArray jarray = null;
                        try {
                            jarray = jobj.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject events = jarray.getJSONObject(i);
                                //System.out.println(events);
                                EventList.add(events);
                            }
                            Iterator<JSONObject> iter = EventList.iterator();
                            JSONObject temp = new JSONObject();
                            long currTime = System.currentTimeMillis();
                            while (iter.hasNext()) {
                                temp = iter.next();
                                String start = temp.getString("start_time");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
                                long startTime = dateFormat.parse(start).getTime();
                                long endTime = dateFormat.parse(start).getTime() + 3600000 * 2;
                                if (startTime - currTime < (3600000 * TimeFrame) && endTime - currTime > 0){
                                    //System.out.println(temp.getString("id") + " was added");
                                    //System.out.println(temp);
                                    //System.out.println(temp.getString("place"));
                                    try {
                                        FacebookList.add(new facebookObject(temp.getString("name"), temp.getString("description"), temp.getString("id"), temp.getString("start_time"), temp.getJSONObject("place").getJSONObject("location").getString("longitude"), temp.getJSONObject("place").getJSONObject("location").getString("latitude")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    //System.out.println(temp.getString("id") + " was removed");
                                    //try {
                                    //    System.out.println(temp);
                                    //    System.out.println(temp.getJSONObject("place"));
                                    //} catch (JSONException e) {
                                    //    e.printStackTrace();
                                    //}
                                    //System.out.println(temp.getString("start_time"));
                                    //System.out.print("start - curr = ");
                                    //System.out.println((startTime - currTime) / 3600000);
                                    //System.out.print("end - curr = ");
                                    //System.out.println((endTime - currTime) / 3600000);
                                    iter.remove();
                                }
                            }
                            Iterator<facebookObject> iterFace = FacebookList.iterator();
                            while (iterFace.hasNext()) {
                                facebookObject x = iterFace.next();
                                //System.out.println("long: " + x.getLongitude());
                                //System.out.println("lat: " + x.getLatitude());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e){
                        }
                        MapsActivity.instance.createEventPins(FacebookList);
                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("q", "madison, wi");
        parameters.putString("type", "event");
        parameters.putString("fields", "id, name, description, place, start_time");
        parameters.putString("limit", "200");
        System.out.println(parameters);
        PublicEventRequest.setParameters(parameters);
        PublicEventRequest.executeAsync();
        return this.FacebookList;
    }
}