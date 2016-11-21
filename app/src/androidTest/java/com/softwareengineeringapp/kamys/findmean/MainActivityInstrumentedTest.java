package com.softwareengineeringapp.kamys.findmean;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Tests that all UI components function and exist on the main activity screen
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.kamys.findmean", appContext.getPackageName());
    }

    @Test
    public void guestLoginResponseTest() throws Exception{
        Instrumentation.ActivityMonitor mIntermediateCheck = InstrumentationRegistry.getInstrumentation()
                .addMonitor(IntermediateMap.class.getName(), null, false);

        Instrumentation.ActivityMonitor mMapCheck = InstrumentationRegistry.getInstrumentation()
                .addMonitor(MapsActivity.class.getName(), null, false);

        Espresso.onView(withId(R.id.guestLogin))
                .perform(ViewActions.click());

        IntermediateMap mIntermediateAct = (IntermediateMap) mIntermediateCheck.waitForActivity();
        assertNotNull("ERROR: Guest Login doesn't start Intermediate Map Activity", mIntermediateAct);

        MapsActivity mMapAct = (MapsActivity) mMapCheck.waitForActivity();
        assertNotNull("ERROR: Intermediate Activity doesn't start Maps Activity", mMapAct);
    }

    @Test
    public void facebookLoginResponseTest() throws Exception{

        Espresso.onView(withId(R.id.login_button))
                .perform(ViewActions.click());
        Espresso.pressBack();
        Espresso.onView(withText("Login attempt cancelled."))
                .check(matches(isDisplayed()));


    }
}
