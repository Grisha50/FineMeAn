package com.softwareengineeringapp.kamys.findmean;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.test.rule.ActivityTestRule;


import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

/**
 * Created by Jared on 11/16/2016.
 */

public class MapsActvityInstrumentedTest {
    private ArrayList<buildingObject> mList;
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void buildingSetup(){
        mList = new ArrayList<buildingObject>();
        mList.add(new buildingObject(true, true, -89.4067, 43.0755, true, true, "Van Hise"));
        MapsActivity.buildings = mList;
    }

    @Test
    public void objectIntegrityTest(){
        buildingObject b = mList.get(0);
        assertTrue("ERROR: Building Object fields differ from instantiation",
                b.getBath() && b.getDrink() && b.getElev() && b.getHand() &&
                        !b.getInfo().equals("") && Double.compare(b.longitude(), -89.4067)
                        == 0 && Double.compare(b.latitude(), 43.0755) == 0);

        facebookObject f = new facebookObject("Test!", "3:00PM", "11/23/2016", "1102 Regent St.");
        assertTrue("ERROR: Facebook Object fields differ from instantiation",
                f.getAddress().equals("1102 Regent St.") && f.getDate().equals("11/23/2016") &&
                        f.getTime().equals("3:00PM") && f.getEventName().equals("Test!"));
    }

    @Test
    public void addBuildingToMapTest(){
        assertNotNull("ERROR: Marker not properly added to map.", Espresso.onView(withText("Van Hise")));
    }
}
