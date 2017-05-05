package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Date;

public class StepsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SM;
    //private Sensor mySensor;

    TextView tv_steps;
    TextView tv_date;
    TextView tv_dist;
    TextView tv_cal;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_v2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tv_steps = (TextView) findViewById(R.id.tv_steps);
        tv_dist = (TextView) findViewById(R.id.tv_dist);
        tv_cal = (TextView) findViewById(R.id.tv_cal);
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
     //   mySensor = SM.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
     //   SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(currentDateTimeString);


    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor  countSensor = SM.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor!=null){
            SM.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_personal) {
            Intent i = new Intent(this, MyPreferencesActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            tv_steps.setText(String.valueOf(event.values[0]));

            tv_dist.setText(String.format("%.1f", event.values[0]*0.45*1.8) + " m");
         //   tv_dist.setText(String.valueOf(event.values[0]*0.45*1.8) + " m");
            tv_cal.setText(String.format("%.1f", event.values[0]*0.045) + " kcal");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
