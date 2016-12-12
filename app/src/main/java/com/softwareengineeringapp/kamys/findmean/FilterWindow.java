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
    public static boolean filterUpdate;
    public static String[] updatedArgs = new String[5];
    private static int init = 0;
    private Button filterButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(dm.widthPixels*.8), (int)(dm.heightPixels*.4));

        filterUpdate = false;

        filterButton = (Button) findViewById(R.id.filterButtion2);
        final CheckBox checkBox[] = {(CheckBox) findViewById(R.id.checkBox1),
                (CheckBox) findViewById(R.id.checkBox2),
                (CheckBox) findViewById(R.id.checkBox3),
                (CheckBox) findViewById(R.id.checkBox4),
                (CheckBox) findViewById(R.id.checkBox5)};
        final String Keys[] = {"RestroomsKey", "ElevatorsKey", "StudyKey", "RampsKey", "EventsKey"};

        int i = 0;
        for(String key:Keys){
            checkBox[i].setChecked(MainActivity.instance.getPref(key) == 1?true:false);
            if(init == 0){
                checkBox[i].setChecked(false);
            }
            i++;
        }

        init++;
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for(String key:Keys){
                    MainActivity.instance.editPref(key, checkBox[i].isChecked()?1:0);
                    updatedArgs[i] = checkBox[i].isChecked()?"y":"x";
                    i++;
                }
                filterUpdate = true;
                setResult(1);
                finish();
            }
        });
    }
}
