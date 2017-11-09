package com.example.a18.path.parabola;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
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

    float[] location = new float[2];
    private Path mPath;
    private PathMeasure mPathMeasure;
    private ValueAnimator mAnimator;
    private float mLength;

    public ParabolaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);

        setWillNotDraw(false);  // 强制调用draw方法
    }


    Point mStartP;
    Point mControlP;
    Point mEndP;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mStartP = new Point(800, 300);
        mControlP = new Point(600, 100);
        mEndP = new Point(0, h);
        mPath.moveTo(mStartP.x, mStartP.y);
        mPath.quadTo(mControlP.x, mControlP.y, mEndP.x, mEndP.y);

        mPathMeasure = new PathMeasure(mPath, false);

        mLength = mPathMeasure.getLength();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimator = ValueAnimator.ofInt(0, 1);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPathMeasure.getPosTan(mLength * animation.getAnimatedFraction(), location, null);
                invalidate();
            }
        });

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                needDraw = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                needDraw = false;
                invalidate();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
        mAnimator = null;
    }

    boolean needDraw = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (needDraw) {

        canvas.drawCircle(location[0],location[1],30,mPaint);
        }
    }

    public void start() {
        mAnimator.start();
    }
}
