package com.example.a18.path.fluidslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;


public class FluidSlider extends View {

    private Paint bluePaint;
    private Paint redPaint;
    private RectF rectF;
    private RectF warp;


    private int sliderRadius = 40;
    private int sliderStrokeWidth = 10;
    private int touchHeight = 100;
    private PopupWindow popupWindow;

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
        TextView textView = new TextView(getContext());
        textView.setText("aaasdfsdgsdg");
        popupWindow = new PopupWindow(textView,300,400);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        slideX = event.getX();
        invalidate();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                popupWindow.showAtLocation(this, Gravity.TOP,0,0);
                popupWindow.getContentView().animate()
                    .translationY(1000)
                    .setDuration(1000)
                    .start();


                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,getHeight()-touchHeight,getWidth(),getHeight(),bluePaint);

        canvas.drawCircle(slideX,getHeight() - touchHeight - sliderRadius - sliderStrokeWidth,sliderRadius,redPaint);

        canvas.drawRect(warp,redPaint);
    }

    float slideX;
}
