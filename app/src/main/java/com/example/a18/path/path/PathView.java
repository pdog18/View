package com.example.a18.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/5  10 :47
 */

public class PathView extends View {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath;

    Paint redPaint = new Paint();

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private static final String TAG = "PathView";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                Log.e(TAG, String.format("AT_MOST%s", "widthSize"));
                break;
            case MeasureSpec.EXACTLY:
                int i = MeasureSpec.makeMeasureSpec(1080, MeasureSpec.EXACTLY);
                setMeasuredDimension(i, heightMeasureSpec);
                return;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, String.format("UNSPECIFIED%s", "widthmode"));
                break;
        }
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                Log.e(TAG, String.format("AT_MOST%s", "heightMode"));
                int i = MeasureSpec.makeMeasureSpec(heightSize / 2, MeasureSpec.AT_MOST);
                setMeasuredDimension(widthMeasureSpec, i);
                return;

            case MeasureSpec.EXACTLY:
                Log.e(TAG, String.format("EXACTLY%s", "heightMode"));
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, String.format("UNSPECIFIED%s", "heightMode"));
                break;
        }

        Log.e(TAG, widthSize + "");
        Log.e(TAG, heightSize + "");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private void init() {
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);


        redPaint.setStrokeWidth(3);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);


        mPath = new Path();
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CCW);
        mPath.setLastPoint(-300, 300);
        mPath.offset(300, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() >> 1, getHeight() >> 1);

        canvas.drawLine(-getWidth(), 0, getWidth(), 0, redPaint);
        canvas.drawLine(0, -getHeight(), 0, getHeight(), redPaint);
        canvas.drawPath(mPath, mPaint);
    }
}
