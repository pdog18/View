package com.example.a18.path.fluidslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class FluidSlider extends View {

    private Paint bluePaint;
    private Paint redPaint;
    private RectF rectF;
    private RectF warp;

    public FluidSlider(Context context) {
        this(context, null);
    }

    public FluidSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FluidSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.BLUE);

        redPaint = new Paint(bluePaint);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(20);
        redPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0,h/2,w,h);

        warp = new RectF(0,0,w,h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        slideX = event.getX();
        invalidate();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(slideX,getHeight()/4,40,redPaint);
        canvas.drawRect(rectF,bluePaint);

        canvas.drawRect(warp,redPaint);
    }

    float slideX;
}
