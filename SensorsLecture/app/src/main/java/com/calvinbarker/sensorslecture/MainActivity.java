package com.calvinbarker.sensorslecture;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List l = sm.getSensorList(Sensor.TYPE_ALL);
        Sensor s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(this, s, 1000000);

        for (int i = 0; i < l.size(); i++) {
            System.out.println("Sensor: " + l.get(i).toString());
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        System.out.println(mySensor);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
