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

import org.w3c.dom.Text;

import java.util.Random;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor s;
    private long lastUpdate = System.currentTimeMillis();
    private PlotView pv;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView tv = (TextView) findViewById(R.id.plot_title);
        pv = (PlotView)findViewById(R.id.pv);

        if (getIntent().getStringExtra("sensorName").equals("accel")) {
            s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

            tv.setText("Accelerometer");
        } else if (getIntent().getStringExtra("sensorName").equals("light")) {
            s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

            tv.setText("Light Sensor");
        }
    }

    public void goBack(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /*public void foo(View v) {

        System.out.println("Press!");
        pv = (PlotView)findViewById(R.id.pv);

        pv.addPoint(rand.nextFloat() * 100);
        pv.invalidate();
    }*/

    /*public void foo() {
        pv = (PlotView)findViewById(R.id.pv);

        pv.addPoint(rand.nextFloat() * 100);
        pv.invalidate();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (s.getType() == Sensor.TYPE_ACCELEROMETER
                && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            long interval = curTime - lastUpdate;
            if (interval > 1000) {
                lastUpdate = curTime;

                float xSq = (float) Math.pow((double) x, 2);
                float ySq = (float) Math.pow((double) y, 2);
                float zSq = (float) Math.pow((double) z, 2);

                float val = (float) Math.pow(xSq + ySq + zSq, 0.5);

                pv.addPoint(val, interval);
                pv.invalidate();
            }
        } else if (s.getType() == Sensor.TYPE_LIGHT
                && event.sensor.getType() == Sensor.TYPE_LIGHT) {
            long curTime = System.currentTimeMillis();

            int interval = (int) (curTime - lastUpdate);

            if ((interval) > 1000) {
                lastUpdate = curTime;

                pv.addPoint(event.values[0], curTime);
                pv.invalidate();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
