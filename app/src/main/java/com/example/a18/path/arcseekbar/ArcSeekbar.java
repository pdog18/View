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
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by pdog on 2017/12/1.
 */

public class ArcSeekbar extends View {
    private static final String TAG = "DragView";
    private Path dashArcPath;
    private PathEffect dashArcPathEffect;
    private Paint arcPathPaint;
    private float arcPathWidth = 3;
    private float shadowWidth = 5;
    private PointF resultPoint = new PointF();
    private PointF canvasPoint = new PointF();

    private float topGap;

    private Path solidArcPath;
    private Paint slideblockPaint;
    private float blockRadius;

    Region mBlockRegion;


    private float rightGap;
    private float dashRadius;

    private float lastX;

    private int cellCount = 10;
    private int index;

    ProgressChangeListener changeListener;

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
        rightGap = dp(getContext(), 100);
        arcPathWidth = dp(getContext(), 12);
        arcPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        arcPathPaint.setStrokeWidth(arcPathWidth);
        arcPathPaint.setColor(Color.WHITE);
        arcPathPaint.setStyle(Paint.Style.STROKE);


        blockRadius = dp(getContext(), 120);
        shadowWidth = dp(getContext(), 12);
        slideblockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        slideblockPaint.setColor(Color.WHITE);
        slideblockPaint.setStrokeWidth(blockRadius * 2);
        slideblockPaint.setShadowLayer(shadowWidth, shadowWidth, shadowWidth, Color.DKGRAY);
        slideblockPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST
                && heightMode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("All measure mode is AT_MOST");
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize / 2, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * 2, MeasureSpec.EXACTLY);
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dashRadius = (int) (getHeight() / 2.5);

        topGap = getHeight() / 2 - dashRadius;

        createDashPath();

        createSolidPath();

        createBlockSlideRegion(w, h);

        refreshPoint(0, dashRadius);      //确保滑块初始位置为上方
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
        float centerX = getWidth() - rightGap;
        float centerY = getHeight() / 2;
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
                generateBlockPoint(getPointOfCircleCenter(event));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                generateNearestBlockPoint(getPointOfCircleCenter(event));
                invalidate();
                if (changeListener != null) {
                    // FIXME: 2017/12/4
                    changeListener.onProgressChanged(index);
                }
                break;
        }
        return true;
    }

    private PointF getPointOfCircleCenter(MotionEvent event) {
        float x = (getWidth() - rightGap - event.getX());
        float y = (getHeight() / 2 - event.getY());

        if (x <= 0 && lastX <= 0) {
            //  如果手指始终在右侧时，那么就不对Y进行调整。
            return canvasPoint;
        }

        if (x < 0) {             // 超出了右侧，那么需要对x，y进行调整
            x = 0;
            if (y >= 0) {        // 在上方
                y = dashRadius;
            } else {             // 在下方
                y = -dashRadius;
            }

        }
        lastX = x;

        canvasPoint.set(x, y);
        return canvasPoint;
    }

    @SuppressWarnings("all")
    private void generateNearestBlockPoint(PointF pointF) {
        double hypotenuse = dashRadius;    //斜边长度
        double tan = Math.atan2(pointF.x, pointF.y);              //角度 ，tan值

        double angle = 180 * tan / Math.PI;

        double cellPercent = 180.0f / cellCount;        //每个cell享有对角度大小     20 = 9
        index = (int) Math.floor(angle / cellPercent + 0.5f);        //最接近的

        double ajustedAngle = 180 * (index * 1.0f / cellCount);
        double ajustedTan = Math.PI / 180.0f * ajustedAngle;

        double x = hypotenuse * Math.sin(ajustedTan);     //角度对边
        double y = hypotenuse * Math.cos(ajustedTan);     //角度邻边

        refreshPoint(x, y);
    }


    @SuppressWarnings("all")
    private void generateBlockPoint(PointF pointF) {
        // 1. 算出触点对应圆心的斜边
        double hypotenuse = Math.hypot(pointF.x, pointF.y);

        // 2. 计算斜边对应半径的比例
        double ratio = hypotenuse / dashRadius;
        // 3. 根据比例算出对应的x 和 y
        refreshPoint(pointF.x / ratio, pointF.y / ratio);
    }


    private void refreshPoint(double x, double y) {
        resultPoint.set(((float) x), ((float) y));
    }


    public void setCellCount(int count) {
        this.cellCount = count;
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
        canvas.translate(getWidth() - rightGap, getHeight() / 2);
        canvas.drawPoint(-resultPoint.x, -resultPoint.y, slideblockPaint);
        canvas.restoreToCount(save);
    }


    interface ProgressChangeListener {
        void onProgressChanged(float percent);
    }

    public void setOnProgressChangeListener(ProgressChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public static float dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale + 0.5f);
    }
}
