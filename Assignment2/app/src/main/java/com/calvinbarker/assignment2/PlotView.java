package com.calvinbarker.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by barkerc1 on 2/25/17.
 */

public class PlotView extends View {

    private ArrayList<DataPoint> points = new ArrayList<>();
    private ArrayList<DataPoint> averages = new ArrayList<>();
    private ArrayList<DataPoint> standardDevs = new ArrayList<>();

    long start = System.currentTimeMillis();

    //Date startTime = new Date(start);
    //Date minTime = new Date(start);
    //Date maxTime = new Date(start);


    private long lastUpdate = System.currentTimeMillis();
    private int averageCount = 5;
    private int currentSec = 0;

    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#929292"));

        float width = getWidth();
        float height = getHeight();

        float maxY = 0;
        float minY = Integer.MAX_VALUE;

        float maxX;
        float minX;

        if (points.size() > 0){
            maxX = points.get( points.size() - 1 ).time;
            minX = points.get(0).time;

            //minTime = new Date(points.get(0).time);
            //maxTime = new Date(points.get( points.size() - 1 ).time);
        } else {
            maxX = 1;
            minX = 0;
        }

        for (DataPoint d : points) {
            if (d.value > maxY)
                maxY = d.value;
            if (d.value < minY)
                minY = d.value;
        }
        for (DataPoint d : standardDevs) {
            if (d.value > maxY)
                maxY = d.value;
            if (d.value < minY)
                minY = d.value;
        }
        for (DataPoint d : averages) {
            if (d.value > maxY)
                maxY = d.value;
            if (d.value < minY)
                minY = d.value;
        }

        float y_range = (maxY - minY) * (float) 1.07;
        float x_range = maxX - minX;


        // Draws the grid lines
        canvas.drawLine(0, 0, width, 0, gridPaint);
        canvas.drawLine(0, 0, 0, height, gridPaint);
        canvas.drawLine(0, height, width, height, gridPaint);
        canvas.drawLine(width, 0, width, height, gridPaint);

        float margin = height * (float) .1;

        int divs = 10;
        for (float i = 0; i <= divs; i++) {
            //horizontal lines
            canvas.drawLine(margin, margin + i / (float) divs * (float) .8 * height,
                            width - margin, margin + i / (float) divs * (float) .8 * height,
                            gridPaint);

            // Calculate  y-axis labels
            String yLabel = Float.toString(i / (float) divs * y_range + minY);
            if (yLabel.length() > 3) {
                yLabel = yLabel.substring(0, 4);
            }
            canvas.drawText(yLabel,
                            (float) 0.055 * width,
                            margin + (float) .8 * height - (i / (float) divs) * (float) .8 * height,
                            gridPaint);

            //verical lines
            canvas.drawLine(margin + i / (float) divs * (float) .8 * width, margin,
                    margin + i / (float) divs * (float) .8 * height, width - margin,
                    gridPaint);
        }

        gridPaint.setTextSize(24);
        canvas.drawText("Time (Seconds)", width * (float) .6, height * (float) .98, gridPaint);

        canvas.save();
        canvas.rotate((float) -90, width / 2, height / 2);
        canvas.drawText("Data", width * (float) .8, height * (float) .05, gridPaint);
        canvas.restore();



        // Plot the running averages
        gridPaint.setColor(Color.RED);
        canvas.drawText("Mean", width * (float) .25, height * (float) .98, gridPaint);

        for(float i = 0; i < averages.size(); i++) {
            if (averages.get((int) i).value != -1) {
                float x = (margin + (averages.get((int) i).time - minX)/x_range * (float) 0.8 * width);
                float y = height - (((averages.get((int) i).value - minY )/ y_range * height) * (float) .8 + margin);

                canvas.drawCircle(x, y, 5f, gridPaint);

                // Draw the line
                if (i > 0 && averages.get((int) i - 1).value != (float) -1) {
                    float xPrev = (margin + (averages.get((int) i - 1).time - minX)/x_range * (float) 0.8 * width);
                    float yPrev = height - (((averages.get((int) i - 1).value - minY )/ y_range * height) * (float) .8 + margin);

                    canvas.drawLine(x, y, xPrev, yPrev, gridPaint);
                }
            }
        }

        // Plot the running standard deviations
        gridPaint.setColor(Color.GREEN);
        canvas.drawText("StdDev", width * (float) .4, height * (float) .98, gridPaint);

        for(float i = 0; i < standardDevs.size(); i++) {
            if (standardDevs.get((int) i).value != -1) {
                float x = (margin + (standardDevs.get((int) i).time - minX)/x_range * (float) 0.8 * width);
                float y = height - (((standardDevs.get((int) i).value - minY )/ y_range * height) * (float) .8 + margin);

                canvas.drawCircle(x, y, 5f, gridPaint);

                // Draw the line
                if (i > 0 && standardDevs.get((int) i - 1).value != (float) -1) {
                    float xPrev = (margin + (standardDevs.get((int) i - 1).time - minX)/x_range * (float) 0.8 * width);
                    float yPrev = height - (((standardDevs.get((int) i - 1).value - minY )/ y_range * height) * (float) .8 + margin);

                    canvas.drawLine(x, y, xPrev, yPrev, gridPaint);
                }
            }
        }

        // Calculate and draw data points
        gridPaint.setColor(Color.BLUE);
        canvas.drawText("Value", width * (float) .1, height * (float) .98, gridPaint);

        for (float i = 0; i < points.size(); i++) {

            gridPaint.setColor(Color.BLUE);

            float x = (margin + (points.get((int) i).time - minX)/x_range * (float) 0.8 * width);
            float y = height - (((points.get((int) i).value - minY )/ y_range * height) * (float) .8 + margin);

            canvas.drawCircle(x, y, 5f, gridPaint);

            // Draw the line
            if (i > 0) {
                float xPrev = (margin + (points.get((int) i - 1).time - minX)/x_range * (float) 0.8 * width);
                float yPrev = height - (((points.get((int) i - 1).value - minY )/ y_range * height) * (float) .8 + margin);

                canvas.drawLine(x, y, xPrev, yPrev, gridPaint);
            }

            gridPaint.setColor(Color.parseColor("#929292"));
            int xInt = currentSec + (int) i;

            canvas.drawText(Integer.toString(xInt), margin + i / ((float) points.size() - 1) * (float) .8 * width, (float) 0.93 * height, gridPaint);

            /*Date currTime = new Date(points.get((int) i).time);
            int timeLabel = currTime.getSeconds() - minTime.getSeconds() - startTime.getSeconds();
            canvas.drawText(Integer.toString(timeLabel), margin + i / (float) divs * (float) .8 * width, (float) 0.93 * height, gridPaint);*/
        }
    }

    private float getRunningMean() {
        float sum = 0;
        for (int i = 1; i <= averageCount; i++) {
            sum += points.get(points.size() - i).value;
        }

        return sum / (float) averageCount;
    }

    private float getRunningStdDev(float mean) {
        float temp = 0;
        for (int i = 1; i <= averageCount; i++) {
            temp += (points.get(points.size() - i).value - mean) * (points.get(points.size() - i).value - mean);
        }

        return (float) Math.sqrt(temp / averageCount);
    }

    public void addPoint(float num, long t) {

        long prevTime = 0;
        currentSec++;

        if (points.size() > 0) {
            prevTime = points.get( points.size() - 1 ).time;
        }
        long offset = t - lastUpdate + prevTime;
        lastUpdate = System.currentTimeMillis();

        // If the length of the list exceeds 10, drop the first element
        if (points.size() > 10) {
            points.remove(0);
        }



        //points.add(num);
        points.add(new DataPoint(num, offset));

        // Computing the average and stand dev of the past five points
        if (averages.size() > 10) {
            averages.remove(0);
        }
        if (standardDevs.size() > 10) {
            standardDevs.remove(0);
        }
        if (points.size() > 4) {
            float mean = getRunningMean();
            averages.add(new DataPoint(mean, offset));
            standardDevs.add(new DataPoint(getRunningStdDev(mean), offset));
        } else {
            averages.add(new DataPoint((float) -1, offset));
            standardDevs.add(new DataPoint((float) -1, offset));
        }
    }

    private class DataPoint {
        long time;
        float value;

        DataPoint(float v, long t) {
            time = t;
            value = v;
        }
    }
}

