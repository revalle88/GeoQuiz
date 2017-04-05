package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class StepsActivity extends Activity implements SensorEventListener {
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
    public void onSensorChanged(SensorEvent event) {
        if (running){
            tv_steps.setText(String.valueOf(event.values[0]));
            tv_dist.setText(String.valueOf(event.values[0]*0.45*1.8) + " m");
            tv_cal.setText(String.valueOf(event.values[0]*0.045) + " kcal");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
