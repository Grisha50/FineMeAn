package com.softwareengineeringapp.kamys.findmean;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        if (MainActivity.instance.getPref(getString(R.string.RESTROOMS)) == 1) {
            checkBox1.setChecked(true);
        }
        if (MainActivity.instance.getPref(getString(R.string.ELEVATORS)) == 1) {
            checkBox2.setChecked(true);
        }
        if (MainActivity.instance.getPref(getString(R.string.FOUNTAINS)) == 1) {
            checkBox3.setChecked(true);
        }
        if (MainActivity.instance.getPref(getString(R.string.RAMPS)) == 1) {
            checkBox4.setChecked(true);
        }
        if (MainActivity.instance.getPref(getString(R.string.EVENTS)) == 1) {
            checkBox5.setChecked(true);
        }
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()) {
                    MainActivity.instance.editPref(getString(R.string.RESTROOMS), 1);
                } else {
                    MainActivity.instance.editPref(getString(R.string.RESTROOMS), 0);
                }
                if (checkBox2.isChecked()) {
                    MainActivity.instance.editPref(getString(R.string.ELEVATORS), 1);
                } else {
                    MainActivity.instance.editPref(getString(R.string.ELEVATORS), 0);
                }
                if (checkBox3.isChecked()) {
                    MainActivity.instance.editPref(getString(R.string.FOUNTAINS), 1);
                } else {
                    MainActivity.instance.editPref(getString(R.string.FOUNTAINS), 0);
                }
                if (checkBox4.isChecked()) {
                    MainActivity.instance.editPref(getString(R.string.RAMPS), 1);
                } else {
                    MainActivity.instance.editPref(getString(R.string.RAMPS), 0);
                }
                if (checkBox5.isChecked()) {
                    MainActivity.instance.editPref(getString(R.string.EVENTS), 1);
                } else {
                    MainActivity.instance.editPref(getString(R.string.EVENTS), 0);
                }
                finish();
            }
        });
    }
}
