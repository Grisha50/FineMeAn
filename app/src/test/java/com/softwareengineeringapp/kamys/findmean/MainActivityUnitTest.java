package com.softwareengineeringapp.kamys.findmean;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Jared on 11/15/2016.
 */
public class MainActivityUnitTest{
    @Test
    public void AllReferencedUIViewsExistTest() throws Exception{
        assertNotNull("ERROR: Undefined Layout: activity_main", R.layout.activity_main);
        assertNotNull("ERROR: Undefined Layout: facebook_login_fragment", R.layout.com_facebook_login_fragment);
        assertNotNull("ERROR: Undefined Layout: intermediate_map_layout", R.layout.blank_layout);
        assertNotNull("ERRPR: Undefined Layout: activity_maps", R.layout.activity_maps);

        assertNotNull("ERROR: Null textview", R.id.info);
        assertNotNull("ERROR: Guest Login button does not exist", R.id.guestLogin);
        assertNotNull("ERROR: Login button does not exist", R.id.login_button);
    }

}
