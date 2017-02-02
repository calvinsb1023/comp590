package com.calvinbarker.lecture3_drawablesandanimation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imv = (ImageView) findViewById(R.id.imv);
        imv.setBackgroundResource(R.drawable.mylist);

        startAnim();
    }

    public void startAnim() {
        ((AnimationDrawable)imv.getBackground()).start();
    }
}
