package com.softwareengineeringapp.kamys.findmean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Andy Ni on 12/8/2016.
 */

public class SettingsWindow extends Activity {
    private static SeekBar seek_bar;
    private static TextView textView;
    private static Button main_menu;
    private static Button back;
    public int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(dm.widthPixels*.8), (int)(dm.heightPixels));
        seekBar();

        main_menu = (Button) findViewById(R.id.main_menu_button);
        back = (Button) findViewById(R.id.back_button);

        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.instance.finishActivity();
                mainView();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void mainView()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void seekBar() {
        seek_bar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView2);
        seek_bar.setMax(10);
        seek_bar.setProgress(MainActivity.instance.getPref("DrawDistKey"));
        textView.setText(seek_bar.getProgress() + " miles from your location");
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distance = progress;
                        textView.setText(distance + " miles from your location");
                        MainActivity.instance.editPref("DrawDistKey", distance);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText(distance + " miles from your location");
                        MainActivity.instance.editPref("DrawDistKey", distance);
                    }
                }
        );
    }
}
