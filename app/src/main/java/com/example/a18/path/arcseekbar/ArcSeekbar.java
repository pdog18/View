package com.example.a18.path.arcseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pdog on 2017/12/1.
 *
 */

public class ArcSeekbar extends View {
    private static final String TAG = "DragView";
    private Path dashArcPath;
    private PathEffect dashArcPathEffect;
    private Paint arcPathPaint;
    private PointF point = new PointF();

    int topGap;

    private Path solidArcPath;
    private Paint slideblockPaint;
    int blockRadius = 40;

    Region mBlockRegion;

    ProgressChangeListener changeListener;

    private int rightGap = 100;
    private int dashRadius;

    private double angle;

    public ArcSeekbar(Context context) {
        this(context, null);
    }

    public ArcSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        arcPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        arcPathPaint.setStrokeWidth(3);
        arcPathPaint.setColor(Color.WHITE);
        arcPathPaint.setStyle(Paint.Style.STROKE);

        slideblockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        slideblockPaint.setColor(Color.WHITE);
        slideblockPaint.setStrokeWidth(blockRadius * 2);
        slideblockPaint.setShadowLayer(5, 5, 5, Color.DKGRAY);
        slideblockPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dashRadius = (int) (getHeight() / 2.5);

        topGap =  getHeight() / 2 - dashRadius;

        createDashPath();

        createSolidPath();

        createBlockSlideRegion(w, h);

        point.set(0,dashRadius);            //确保滑块初始位置为上方
    }


    private void createDashPath() {
        dashArcPath = new Path();
        RectF dashRectF = new RectF(-dashRadius, -dashRadius, dashRadius, dashRadius);
        dashArcPath.addArc(dashRectF, 0, 360);
        dashArcPathEffect = new DashPathEffect(new float[]{10, 10}, 0);
    }

    private void createSolidPath() {
        solidArcPath = new Path();
        int solidRadius = (int) (dashRadius * 0.8);
        RectF solidRectF = new RectF(-solidRadius, -solidRadius, solidRadius, solidRadius);
        solidArcPath.addArc(solidRectF, 90, 180);
    }

    private void createBlockSlideRegion(int w, int h) {
        Path regionPath = new Path();
        int centerX = getWidth() - rightGap;
        int centerY = getHeight() / 2;
        regionPath.addCircle(centerX, centerY, dashRadius + blockRadius * 2, Path.Direction.CW);
        regionPath.addCircle(centerX, centerY, dashRadius - blockRadius * 2, Path.Direction.CCW);
        Region globalRegion = new Region(0, 0, w, h);
        mBlockRegion = new Region();
        mBlockRegion.setPath(regionPath, globalRegion);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return (mBlockRegion.contains(((int) event.getX()), ((int) event.getY())));
            case MotionEvent.ACTION_MOVE:
                float eventY = event.getY();
                float eventX = event.getX();

                generateBlockPoint(eventX,eventY);

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                generateNearestBlockPoint();

                invalidate();
                if (changeListener != null) {
                    // FIXME: 2017/12/4
                    changeListener.onProgressChanged(1);
                }
                break;
        }
        return true;
    }

    private void generateNearestBlockPoint() {
        double a = angle;
        double y = Math.cos(a) * dashRadius;
        double x = Math.sin(a) * dashRadius;

        Log.d(TAG, "y = Math.cos(a) * dashRadius;    " + y);
        Log.d(TAG, "x = Math.sin(a) * dashRadius;    " + x);

    }

    @SuppressWarnings("all")
    private void generateBlockPoint(float eventX, float eventY) {
        float x = (getWidth() - rightGap - eventX);
        float y =  (getHeight() / 2 - eventY);
        angle =  Math.atan2(x,y);

        // 1. 算出触点对应圆心的斜边
        double eventZ = Math.hypot(x, y);

        // 2. 计算斜边对应半径的比例
        double ratio = eventZ / dashRadius;
        // 3. 根据比例算出对应的x 和 y
        point.set(((float) (x / ratio)), ((float) (y / ratio)));
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArcPath(canvas);

        drawSlideBlock(canvas);
    }

    private void drawArcPath(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() - rightGap, getHeight() / 2);
        arcPathPaint.setPathEffect(dashArcPathEffect);
        canvas.drawPath(dashArcPath, arcPathPaint);
        arcPathPaint.setPathEffect(null);
        canvas.drawPath(solidArcPath, arcPathPaint);
        canvas.restore();
    }

    private void drawSlideBlock(Canvas canvas) {
        int save = canvas.save();
        canvas.translate(getWidth() - rightGap, getHeight()/2);
        canvas.drawPoint(-point.x, - point.y, slideblockPaint);
        canvas.restoreToCount(save);
    }


    interface ProgressChangeListener {
        void onProgressChanged(float percent);
    }

    public void setOnProgressChangeListener(ProgressChangeListener changeListener) {
        this.changeListener = changeListener;
    }
}
