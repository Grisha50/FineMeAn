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
        mList.add(new buildingObject(true, true, -89.4067, 43.0755,true, true, "Van Hise"));
        MapsActivity.buildings = mList;
    }

    @Test
    public void addBuildingToMapTest(){

        assertNotNull("ERROR: Marker not properly added to map.", Espresso.onView(withText("Van Hise")));

    }
}
