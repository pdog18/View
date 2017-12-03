package com.example.a18.path.scrollchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pdog on 2017/12/2.
 */

public class ScrollChart extends View {
    private static final String TAG = "ScrollChart";
    GestureDetector detector ;

    private Paint paintG;
    private Paint paintW;
    private float[] temp;



    int gapWidth = 5;
    int girdWidth = 110;
    private int maxLength;

    public ScrollChart(Context context) {
        this(context, null);
    }

    public ScrollChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    {
        paintW = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintW.setColor(Color.BLACK);
        paintW.setStyle(Paint.Style.FILL);
        paintW.setTextSize(30);
        paintW.setTextAlign(Paint.Align.CENTER);

        paintG = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintG.setColor(Color.parseColor("#72B916"));



        detector  = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX,0);

                if (getScrollX() <0){
                    scrollTo(0,0);
                }

                if (getScrollX() > maxLength -getWidth()){
                    scrollTo(maxLength-getWidth(),0);
                }

                Log.d(TAG, "onScroll: " + getScrollX()  + "maxlength    =  "+maxLength);
                return true;
            }
        }) ;
    }


    float[] percents = {0.9f, 0.7f, 0.55f, 0.4f, 0.4f, 0.3f, 0.2f, 0.1f};

    {
        temp = new float[20];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = percents[i % percents.length];
        }

        maxLength = (gapWidth + girdWidth) * temp.length;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        int lineGap = getHeight() / 3;
        int startX = 0;
        int stopX = maxLength ;
        Paint p = paintW;
        for (int i = 0; i < 3; i++) {
            int startY = 20 + lineGap * i;
            canvas.drawLine(startX, startY, stopX, startY, p);
        }
        canvas.restore();

        canvas.save();
        canvas.translate(0, getHeight());

        for (int i = 0; i < temp.length; i++) {
            int gridHeight = (int) (getHeight() * temp[i]);
            int left = gapWidth * (i + 1) + girdWidth * i;
            int top = -gridHeight;
            int right = left + girdWidth;
            int bottom = 0;

            canvas.drawRect(left, top, right, bottom, paintG);
        }
        canvas.restore();

    }
}
