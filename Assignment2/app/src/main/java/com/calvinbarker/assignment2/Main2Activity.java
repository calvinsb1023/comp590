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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor s;
    private long lastUpdate = System.currentTimeMillis();
    private long initialTime = System.currentTimeMillis();
    private float lastVal;
    //private long lastTime;
    private PlotView pv;
    private ImageView imv;
    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView tv = (TextView) findViewById(R.id.plot_title);
        pv = (PlotView)findViewById(R.id.pv);
        imv = (ImageView) findViewById(R.id.imv);
        imv.setImageResource(R.drawable.fire);

        if (getIntent().getStringExtra("sensorName").equals("accel")) {
            s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

            tv.setText("Accelerometer");
        } else if (getIntent().getStringExtra("sensorName").equals("light")) {
            s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

            tv.setText("Light Sensor");
        }

        t = new Timer();
        t.schedule(new DataTimer(), 0, 100);
    }

    public void goBack(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void toggleAnimation(View v) {
        PlotView pv = (PlotView) findViewById(R.id.pv);


        if (pv.getVisibility() == View.VISIBLE) {
            pv.setVisibility(View.GONE);
            imv.setVisibility(View.VISIBLE);
        } else {
            pv.setVisibility(View.VISIBLE);
            imv.setVisibility(View.GONE);
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
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (s.getType() == Sensor.TYPE_ACCELEROMETER
                && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            lastUpdate = curTime;

            float xSq = (float) Math.pow((double) x, 2);
            float ySq = (float) Math.pow((double) y, 2);
            float zSq = (float) Math.pow((double) z, 2);

            float val = (float) Math.pow(xSq + ySq + zSq, 0.5);
            lastVal = val;


            if (val <= 10) {
                imv.setImageResource(R.drawable.house_1);
            } else if (val <= 25) {
                imv.setImageResource(R.drawable.house_2);
            } else {
                imv.setImageResource(R.drawable.house_3);
            }

        } else if (s.getType() == Sensor.TYPE_LIGHT
                && event.sensor.getType() == Sensor.TYPE_LIGHT) {
            final SensorEvent e = event;

            long curTime = System.currentTimeMillis();
            float val = e.values[0];
            final int scale = Math.round(1500 * val / s.getMaximumRange());

            lastUpdate = curTime;
            lastVal = val;

            /*if (val <= 1000 ) {
                imv.setImageResource(R.drawable.flame_1);
            } else if (val <= 2000) {
                imv.setImageResource(R.drawable.fire);
            } else {
                imv.setImageResource(R.drawable.flame_3);
            }*/


            imv.setImageResource(R.drawable.sun2);
            imv.requestLayout();
            imv.getLayoutParams().width = scale;
            imv.getLayoutParams().height = scale;

            //imv.postInvalidate();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class DataTimer extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {

                        pv.addPoint(lastVal, System.currentTimeMillis() - initialTime);
                        pv.invalidate();
                        imv.postInvalidate();
                    }
                }
            );
        }
    }
}
