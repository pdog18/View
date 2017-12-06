package com.example.a18.path.scrollchart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class Chart extends View {
    private static final String TAG = "ScrollChart";

    private Paint paintG;
    private Paint paintW;
    private float[] temp;

    private RectF[] rectFs;
    private Region[] regions;

    int drakBlue = Color.parseColor("#5495FC");
    int lightBlue = Color.rgb(192, 219, 254);

    int gapWidth = 5;
    int girdWidth = 110;
    private int maxLength;
    private RectF chartClipRect;
    private ValueAnimator animator;

    int index = 5;
    private int length = 20;

    public Chart(Context context) {
        this(context, null);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    {
        paintW = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintW.setColor(Color.GRAY);
        paintW.setStyle(Paint.Style.FILL);
        paintW.setTextSize(30);
        paintW.setTextAlign(Paint.Align.CENTER);

        paintG = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        maxLength = (gapWidth + girdWidth) * length + gapWidth;


        int measureSpec = MeasureSpec.makeMeasureSpec(maxLength, MeasureSpec.EXACTLY);

        setMeasuredDimension(measureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chartClipRect = new RectF(0, 0, maxLength, h);

        temp = new float[length];
        rectFs = new RectF[length];
        regions = new Region[length];
        Region golbalRegion = new Region(0, 0, w, h);

        for (int i = 0; i < length; i++) {
            int left = (gapWidth + girdWidth) * i;
            RectF rectF = new RectF(left, -getHeight() / 10 * (i+1) , left + girdWidth, 0);
            rectFs[i] = rectF;

            RectF regionRect = new RectF(left, -getHeight(), left + girdWidth, 0);
            Path path = new Path();
            path.addRect(regionRect, Path.Direction.CCW);
            Region region = new Region();
            region.setPath(path,golbalRegion);
            regions[i] = region;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ");
                for (int i = 0; i < regions.length; i++) {
                    boolean contains = regions[i].contains(((int) event.getX()), ((int) event.getY()));
                    Log.d(TAG, "onTouchEvent: contains" + contains);
                }
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ");
                return true;
        }
        return super.onTouchEvent(event);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroundLine(canvas);

        drawChartCell(canvas);
    }

    private void drawChartCell(Canvas canvas) {
        canvas.save();
        canvas.clipRect(chartClipRect);
        canvas.translate(0, getHeight());
        for (int i = 0; i < length; i++) {
            Log.d(TAG, "drawChartCell: " + rectFs[i].left);
            Log.d(TAG, "drawChartCell: " + rectFs[i].top);
            if (i == index) {
                paintG.setColor(drakBlue);
            } else {
                paintG.setColor(lightBlue);
            }
            canvas.drawRect(rectFs[i], paintG);
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
