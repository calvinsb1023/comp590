package com.calvinbarker.mediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mp = MediaPlayer.create(this.getApplicationContext(), R.raw.gong);
        //mp.start();
    }

    public void start(View v) {
        mp = MediaPlayer.create(this.getApplicationContext(), R.raw.gong);
        mp.start();
    }

    public void stop(View v) {
        mp.stop();
    }
}
