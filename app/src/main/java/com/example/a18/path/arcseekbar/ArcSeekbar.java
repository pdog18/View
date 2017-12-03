package com.example.a18.path.arcseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
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
    private Paint dashPathPaint;

    int topGap;

    private Path solidArcPath;
    private Paint slideBlockPaint;
    int blockRadius = 40;


    int mBlockOffsetY = -1;
    int mBlockOffsetX = -1;
    Region mBlockRegion;

    ProgressChangeListener changeListener;

    private int rightGap = 100;
    private int dashRadius;
    private int bodyHeight;
    private int bodyPercent = 20;

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
        dashPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        dashPathPaint.setStrokeWidth(3);
        dashPathPaint.setColor(Color.WHITE);
        dashPathPaint.setStyle(Paint.Style.STROKE);

        slideBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        slideBlockPaint.setColor(Color.WHITE);
        slideBlockPaint.setShadowLayer(5, 5, 5, Color.DKGRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dashRadius = (int) (getHeight() / 2.5);

        mBlockOffsetY = getHeight() / 2 - dashRadius;
        mBlockOffsetX = 0;

        topGap = mBlockOffsetY;
        bodyHeight = dashRadius * 2;

        createDashPath();

        createSolidPath();

        createBlockSlideRegion(w, h);
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
                mBlockOffsetY = (int) eventY;
                mBlockOffsetX = 0;
                int dy = mBlockOffsetY - getHeight() / 2;

                // 3种情况
                if (Math.abs(dy) < dashRadius) {                   // 1， 在圆弧中移动
                    mBlockOffsetX = getOffsetX();
                } else if (dy >= dashRadius) {                     // 2， 超出圆弧下方
                    mBlockOffsetY = getHeight() / 2 + dashRadius;
                } else {                                           // 3， 超出圆弧上方
                    mBlockOffsetY = getHeight() / 2 - dashRadius;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int nearest = getNearest();

                mBlockOffsetY = (int) (nearest * (bodyHeight * 1.0f / bodyPercent) + topGap);
                mBlockOffsetX = getOffsetX();
                invalidate();

                if (changeListener != null) {
                    changeListener.onProgressChanged(nearest);
                }
                break;
        }
        return true;
    }

    private int getNearest() {
        int nearest = 0;

        float percent = (mBlockOffsetY - topGap) / (bodyHeight * 1f);
        float cellHalf = (100f / bodyPercent) / 100f / 2f;

        for (int i = 0; i < bodyPercent * 2; i++) {
            float v = percent - cellHalf * i;
            if (Math.abs(v) <= cellHalf) {
                nearest = (i + 1) / 2;
                break;
            }
        }
        return nearest;
    }


    public int getOffsetX() {
        int dy = mBlockOffsetY - getHeight() / 2;
        double x = Math.sqrt(dashRadius * dashRadius - dy * dy);
        return ((int) -x);
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
        dashPathPaint.setPathEffect(dashArcPathEffect);
        canvas.drawPath(dashArcPath, dashPathPaint);
        dashPathPaint.setPathEffect(null);
        canvas.drawPath(solidArcPath, dashPathPaint);
        canvas.restore();
    }

    private void drawSlideBlock(Canvas canvas) {
        int save = canvas.save();
        canvas.translate(getWidth() - rightGap, 0);
        canvas.translate(mBlockOffsetX, mBlockOffsetY);
        canvas.drawCircle(0, 0, blockRadius, slideBlockPaint);
        canvas.restoreToCount(save);
    }


    interface ProgressChangeListener {
        void onProgressChanged(float percent);
    }

    public void setOnProgressChangeListener(ProgressChangeListener changeListener) {
        this.changeListener = changeListener;
    }
}
