package com.example.a18.path.arcseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pdog on 2017/12/1.
 */

public class DragView extends View {

    private static final String TAG = "DragView";
    private Paint paint;
    private Paint circlePaint;

    int mThumbOffsetY = -1;
    int mThumbOffsetX = -1;

    int dashRadius;
    int radius = 40;

    boolean isDraging;

    ProgressChangeListener changeListener;
    private Path dashPath;
    private Path solidPath;
    private PathEffect pathEffect;


    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setShadowLayer(5, 5, 5, Color.DKGRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pathEffect = new DashPathEffect(new float[]{10, 10}, 0);

        dashRadius = (int) (getHeight() / 2.5);
        mThumbOffsetY = getHeight() / 2 - dashRadius;
        mThumbOffsetX = 0;

        dashPath = new Path();
        RectF dashRectF = new RectF(-dashRadius,-dashRadius, dashRadius, dashRadius);
        dashPath.addArc(dashRectF,0,360);
        solidPath = new Path();
        int solidRadius = (int) (dashRadius * 0.8);
        RectF solidRectF = new RectF(-solidRadius,-solidRadius,solidRadius,solidRadius);
        solidPath.addArc(solidRectF,90,180);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // TODO: 2017/12/2 在可移动区域才可以移动
                isDraging = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isDraging) {
                    float eventY = event.getY();
                    mThumbOffsetY = (int) eventY;
                    mThumbOffsetX = 0;
                    int dy = mThumbOffsetY - getHeight() / 2;
                    // 3种情况

                    if (Math.abs(dy) < dashRadius) {                   // 1， 在圆弧中移动
                        mThumbOffsetX = getOffsetX(dy);
                    } else if (dy >= dashRadius) {                     // 2， 超出圆弧下方
                        mThumbOffsetY = getHeight() / 2 + dashRadius;
                    } else {                                          // 3， 超出圆弧上方
                        mThumbOffsetY = getHeight() / 2 - dashRadius;
                    }
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDraging) {
                    isDraging = false;

                    int max = dashRadius * 2;
                    int top = (getHeight() - max) / 2;
                    float percent = (mThumbOffsetY - top) / (max * 1.0f);

                    if (changeListener != null) {
                        changeListener.onProgressChange(percent);
                    }
                }

                // TODO: 2017/12/2 根据百分比 重新调整滑块的位置。然后触发回调

                break;
        }
        return true;
    }


    public int getOffsetX(int dy) {
        double x = Math.sqrt(dashRadius * dashRadius - dy * dy);
        return ((int) -x);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() - 100, getHeight() / 2);
        paint.setPathEffect(pathEffect);
        canvas.drawPath(dashPath,paint);
        paint.setPathEffect(null);
        canvas.drawPath(solidPath,paint);
        canvas.restore();

        int save = canvas.save();
        canvas.translate(getWidth() - 100, 0);
        Log.d(TAG, "onDraw: mThumbOffsetY" + mThumbOffsetY);
        canvas.translate(mThumbOffsetX, mThumbOffsetY);
        canvas.drawCircle(0, 0, radius, circlePaint);
        canvas.restoreToCount(save);
    }

    interface ProgressChangeListener {
        void onProgressChange(float percent);
    }

    public void setOnProgressChangeListener(ProgressChangeListener changeListener) {
        this.changeListener = changeListener;
    }
}
