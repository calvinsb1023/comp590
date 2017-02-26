package com.calvinbarker.assignment2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor accelerometer;
    private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);




        if (accelerometer == null) {
            setStatus((TextView) findViewById(R.id.accel_status), "Not Found");
            setRange((TextView) findViewById(R.id.accel_range), "N/A");
            setResolution((TextView) findViewById(R.id.accel_resolution), "N/A");

        } else {
            setStatus((TextView) findViewById(R.id.accel_status), "Found");
            setRange((TextView) findViewById(R.id.accel_range), accelerometer.getMaximumRange());
            setResolution((TextView) findViewById(R.id.accel_resolution), accelerometer.getResolution());

            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (light == null) {
            setStatus((TextView) findViewById(R.id.light_status), "Not Found");
            setRange((TextView) findViewById(R.id.light_range), "N/A");
            setResolution((TextView) findViewById(R.id.light_resolution), "N/A");

        } else {
            setStatus((TextView) findViewById(R.id.light_status), "Found");
            setRange((TextView) findViewById(R.id.light_range), light.getMaximumRange());
            setResolution((TextView) findViewById(R.id.light_resolution), light.getResolution());

            sm.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    public void setStatus(TextView tv, String s){
        tv.setText("Status: " + s);
    }

    public void setRange(TextView tv, float range) {
        tv.setText("Range: " + range);
    }

    public void setRange(TextView tv, String msg) {
        tv.setText("Range: " + msg);
    }

    public void setResolution(TextView tv, float res) {
        tv.setText("Resolution: " + res);
    }

    public void setResolution(TextView tv, String msg) {
        tv.setText("Resolution: " + msg);
    }

    public void startAccel(View v) {

        if (accelerometer == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, the accelerometer could not be found.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();

        } else {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            intent.putExtra("sensorName", "accel");

            startActivity(intent);
        }
    }

    public void startLight(View v) {

        if (light == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, the light sensor could not be found.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();

        } else {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            intent.putExtra("sensorName", "light");

            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (light != null) sm.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
