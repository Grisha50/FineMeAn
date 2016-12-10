package com.softwareengineeringapp.kamys.findmean;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by Andy Ni on 12/8/2016.
 */

public class FilterWindow extends Activity {

    private Button filterButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(dm.widthPixels*.8), (int)(dm.heightPixels*.4));

        filterButton = (Button) findViewById(R.id.filterButtion2);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
