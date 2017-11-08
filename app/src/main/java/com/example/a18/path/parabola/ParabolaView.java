package com.example.a18.path.parabola;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/8  15 :05
 */

public class ParabolaView extends View {

    private Paint mPaint;

    int[] location = new int[2];
    private Path mPath;
    private PathMeasure mPathMeasure;

    public ParabolaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    int minWidth = 40;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            int size = MeasureSpec.getSize(widthMeasureSpec);
            System.out.println(size + "        === size");
            size = minWidth;
            int i = MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
            setMeasuredDimension(i, heightMeasureSpec);
        }
        if (mode2 == MeasureSpec.AT_MOST) {
            int size = MeasureSpec.getSize(heightMeasureSpec);
            System.out.println(size + "        === size");
            size = minWidth;
            int i = MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
            setMeasuredDimension(i, i);
        }

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);


        //获得一个扔物线


    }

    PointF startPoint;
    PointF controlPoint;
    PointF endPoint;

    public void setControlPoint(PointF controlPoint) {
        this.controlPoint = controlPoint;
    }

    public void setEndPoint(PointF endPoint) {
        this.endPoint = endPoint;
    }


    boolean first = true;

    @SuppressLint("NewApi")
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (first) {
            first = false;

            getLocationOnScreen(location);
            System.out.println("onSizeChanged       " + location[0] + "          " + location[1]);

            startPoint = new PointF(location[0], location[1]);
            if (endPoint == null) {
                Point point = new Point();

                getDisplay().getSize(point);
                endPoint = new PointF(0, point.y);
            }
            if (controlPoint == null) {
                controlPoint = new PointF(startPoint.x - 50, startPoint.y - 100);
            }


            mPath = new Path();
            mPath.moveTo(startPoint.x, startPoint.y);
            mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);


            mPathMeasure = new PathMeasure(mPath, false);
        }

    }


    ValueAnimator mValueAnimator = ValueAnimator.ofInt(0, 1);

    public void start() {
        final float length = mPathMeasure.getLength();

        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                float curr = length * animatedFraction;
                float[] xy = new float[2];
                mPathMeasure.getPosTan(curr, xy, null);

                PointF pointF = new PointF(xy[0], xy[1]);
                float x = pointF.x - startPoint.x - getTranslationX();
                float y = pointF.y - startPoint.y - getTranslationY();

                System.out.println(x + " xxxxxxxxxxxxx");
                System.out.println(y + " yyyyyyyyyyy");
                setTranslationX(x);
                setTranslationY(y);
                // FIXME: 2017/11/8 使用setTranslation 不能超过父容器的padding区域

            }
        });

        mValueAnimator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 10, mPaint);
    }
}
