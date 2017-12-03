package com.example.a18.path.scrollchart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;


public class ScrollChart extends View {
    private static final String TAG = "ScrollChart";
    GestureDetector detector;

    private Paint paintG;
    private Paint paintW;
    private float[] temp;

    int gapWidth = 5;
    int girdWidth = 110;
    private int maxLength;
    private RectF chartClipRect;
    private ValueAnimator animator;

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


        detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                scrollBy((int) distanceX, 0);
//
//                if (getScrollX() < 0) {
//                    scrollTo(0, 0);
//                }
//
//                if (getScrollX() > maxLength - getWidth()) {
//                    scrollTo(maxLength - getWidth(), 0);
//                }
//
//                Log.d(TAG, "onScroll: " + getScrollX() + "maxlength    =  " + maxLength);
//                return true;
//            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (velocityX > 0) {// 向右
                    Toast.makeText(getContext(), "用户意图方向右“x>0”", Toast.LENGTH_SHORT).show();
                }

                if (velocityX < 0) {// 向左
                    Toast.makeText(getContext(), "用户意图方向左“x<0”", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "onFling: " + velocityX);


                scrollBy((int) -velocityX, 0);

                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                }

                if (getScrollX() > maxLength - getWidth()) {
                    scrollTo(maxLength - getWidth(), 0);
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chartClipRect = new RectF(0, 0, maxLength, h);
    }


    public void startAnim() {
        animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(animation -> {
            float animatedFraction = animation.getAnimatedFraction();
            chartClipRect.top = getHeight() - getHeight() * animatedFraction;
            Log.d(TAG, "startAnim: " + chartClipRect.top);
            invalidate();
        });
        animator.setDuration(3000);
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroundLine(canvas);

        drawChartCell(canvas);
    }

    private void drawChartCell(Canvas canvas) {
        canvas.save();
        canvas.clipRect(chartClipRect);
        for (int i = 0; i < temp.length; i++) {
            int gridHeight = (int) (getHeight() * temp[i]);
            int left = gapWidth * (i + 1) + girdWidth * i;
            int top = getHeight() - gridHeight;
            int right = left + girdWidth;
            int bottom = getHeight();

            canvas.drawRect(left, top, right, bottom, paintG);
        }
        canvas.restore();
    }

    private void drawBackgroundLine(Canvas canvas) {
        canvas.save();
        int lineGap = getHeight() / 3;
        int startX = 0;
        int stopX = maxLength;
        Paint p = paintW;
        for (int i = 0; i < 3; i++) {
            int startY = 20 + lineGap * i;
            canvas.drawLine(startX, startY, stopX, startY, p);
        }
        canvas.restore();

    }
}
