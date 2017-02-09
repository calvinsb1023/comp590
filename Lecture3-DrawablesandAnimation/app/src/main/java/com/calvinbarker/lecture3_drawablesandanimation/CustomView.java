package com.calvinbarker.lecture3_drawablesandanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by barkerc1 on 2/2/17.
 */

public class CustomView extends View {


    public CustomView(Context con){
        super(con);
    }
    public CustomView(Context con, AttributeSet att){
        super(con, att);
    }
    public CustomView(Context con, AttributeSet att, int s){
        super(con, att, s);
    }
    public CustomView(Context con, AttributeSet att, int s1, int s2){
        super(con, att, s1);
    }

    /**
     * Override onDraw method form VIew class, which draws a line and a circle
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawLine(5, 5, getWidth() - 5, getHeight() - 5, p);
        canvas.drawCircle(MainActivity.X, MainActivity.Y, 5f, p);

    }

}
