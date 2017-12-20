package com.example.a18.path.smartanalysis;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.List;

import timber.log.Timber;

import static com.example.a18.path.Utils.dp2px;

/**
 * 智能分析图标📈，😨
 */
public class SmartChart extends View {

    private String startDate = "2014/08/09";
    private String endDate = "2017/12/19";
    private String rectDate = "2015/05/12";


    int leftGap = dp2px(20);
    int bottomGap = dp2px(35);
    int chartRegionHeight = dp2px(206);
    float chartRegionWidth;
    private Paint baseLinePaint;
    private Paint slideBlockPaint;
    private Paint linePaint;
    private Paint descriptionPaint;
    private TextPaint textPaint;
    private PointF pointInChart;        //这个点总是处在图表当中，不会超出图片左右两侧

    int topColor = Color.parseColor("#97ed4c");
    int middleColor = Color.parseColor("#47f8d0");
    int bottomColor = Color.parseColor("#ffffff");
    int textColor = Color.parseColor("#e6edff");
    int mainBlue = Color.parseColor("#5092ff");

    private float blockRadius = dp2px(16);

    private Path topPath;
    private Path middlePath;
    private Path bottomPath;

    private Path slideBlockDashPath; //用path 而不是用drawLine 是为了方便画虚线
    private PathEffect dashChartLineEffect;
    private PathEffect slideBlockDashEffect;

    private RectF solidPathClipRect;

    float descriptionWidth;
    float descriptionY;
    private RectF slideTopRect;

    boolean needSlideDescript;
    private Rect animateClipRect = new Rect(0, 0, 0, -chartRegionHeight);
    private ValueAnimator animator;
    private boolean needSlideBlock = false;

    float cellWidth;


    public SmartChart(Context context) {
        this(context, null);
    }

    public SmartChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
        initEffect();
        measureTextWidth();

    }

    MockChartData data;

    public void setData(MockChartData data) {
        if (data.getSize() < 2) throw new NumberFormatException();
        this.data = data;
    }

    private void measureTextWidth() {
        String text = "抵押资产";
        descriptionWidth = textPaint.measureText(text);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //圆心y坐标
        descriptionY = fontMetrics.bottom - (fontMetrics.bottom - fontMetrics.top) / 2;
    }

    private void initEffect() {
        dashChartLineEffect = new DashPathEffect(new float[]{dp2px(4), dp2px(4)}, 0);
        slideBlockDashEffect = new DashPathEffect(new float[]{dp2px(6), dp2px(6)}, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!needSlideBlock) {       //动画完毕之前不支持移动
            return super.onTouchEvent(event);
        }
        Paint paint = slideBlockPaint;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                paint.setStrokeWidth(blockRadius * 1.375f);
                needSlideDescript = true;
                return true;

            case MotionEvent.ACTION_MOVE:
                updatePoint(((int) event.getX()), ((int) event.getY()));

                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                paint.setStrokeWidth(blockRadius);
                needSlideDescript = false;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartRegionWidth = w - leftGap * 2;
        cellWidth = chartRegionWidth / (data.getSize() -1);
        initPoint();

        createPaths();
        solidPathClipRect = new RectF(0, 0, w, -h);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        exposeChart();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        super.onDetachedFromWindow();
    }

    public void exposeChart() {
        animator = ValueAnimator.ofFloat(1.0f);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            animateClipRect.right = (int) (v * chartRegionWidth);
            invalidate();
        });
        animator.addListener(new SimpleAnimator() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bounceSlideBlock();
            }
        });
        animator.start();
    }

    //slideblock 弹动
    private void bounceSlideBlock() {
        needSlideBlock = true;
        pointInChart = new PointF(chartRegionWidth, 0);

        animator.removeAllListeners();
        animator.removeAllUpdateListeners();
        animator = animator.clone();
        animator.setInterpolator(new OvershootInterpolator(3));
        animator.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            float radius = blockRadius * v;
            slideBlockPaint.setStrokeWidth(radius);
            invalidate();
        });
        animator.start();
    }


    private void createPaths() {
        topPath = buildPath(data.getTop());
        middlePath = buildPath(data.getMiddle());
        bottomPath = buildPath(data.getBottom());
    }

    private Path buildPath(List<Integer> list) {
        Path path = new Path();

        path.moveTo(0,valueMap(list.get(0)));

        for (int i = 1; i < list.size(); i++) {
            path.lineTo(cellWidth * i, valueMap(list.get(i)));
        }
        return path;
    }

    private int valueMap(int before) {
        int highest = data.getHighest();
        return (int) (- before * (chartRegionHeight * 1.0f /highest));
    }

    private void initPoint() {
        slideBlockDashPath = new Path();
        slideTopRect = new RectF();
    }


    private void updatePoint(int x, int y) {
        float pointX;
        if (x < leftGap) {      //超出图表左侧
            pointX = 0;
        } else if (x > leftGap + chartRegionWidth) {    // 超出图表右侧
            pointX = chartRegionWidth ;
        } else {
            pointX = x - leftGap;
        }
        pointInChart.set(pointX, y);
        slideBlockDashPath.reset();
        slideBlockDashPath.moveTo(0, 0);
        slideBlockDashPath.rLineTo(0, -chartRegionHeight);

        updateRoundRect(pointInChart.x);


        solidPathClipRect.right = pointInChart.x;

    }

    private void updateRoundRect(float x) {
        //顶部的 圆角矩形内容会发生改变
        float textWidth = textPaint.measureText(rectDate) + dp2px(10);
        float offset = 0;


        if (x - textWidth / 2 < 0) {       //顶部圆角矩形超出图表左侧
            offset = textWidth / 2 - x;
        } else if (x + textWidth / 2 > chartRegionWidth) {        //  顶部圆角矩形超出图表右侧
            offset = chartRegionWidth - (x + textWidth / 2) ;
            Timber.d(">>> %s",offset);
        }

        int top = -(chartRegionHeight + dp2px(30));
        slideTopRect.set(
                -(textWidth / 2) + offset,
                top,
                textWidth / 2 + offset,
                top + dp2px(18));
    }

    private void initPaint() {
        baseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        baseLinePaint.setColor(Color.WHITE);
        baseLinePaint.setStrokeWidth(dp2px(1));

        slideBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        slideBlockPaint.setColor(Color.WHITE);
        slideBlockPaint.setStrokeWidth(blockRadius);
        slideBlockPaint.setStrokeCap(Paint.Cap.ROUND);
        slideBlockPaint.setShadowLayer(dp2px(3), dp2px(2), dp2px(2), Color.BLACK);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2px(2));
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);

        descriptionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        descriptionPaint.setStrokeWidth(dp2px(6));
        descriptionPaint.setStrokeCap(Paint.Cap.ROUND);
        descriptionPaint.setStyle(Paint.Style.STROKE);


        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(dp2px(10));
        textPaint.setColor(textColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!needSlideDescript) {
            drawDescription(canvas);
        }

        int save = canvas.save();
        canvas.translate(leftGap, getHeight() - bottomGap);
        drawCoordinateSystem(canvas);

        canvas.clipRect(animateClipRect);
        drawDashPaths(canvas);
        drawSolidPaths(canvas);
        canvas.restoreToCount(save);

        drawDate(canvas);
        drawSlide(canvas);

    }


    private void drawDashPaths(Canvas canvas) {
        linePaint.setPathEffect(dashChartLineEffect);
        linePaint.setColor(topColor);
        canvas.drawPath(topPath, linePaint);
        linePaint.setColor(middleColor);
        canvas.drawPath(middlePath, linePaint);
        linePaint.setColor(bottomColor);
        canvas.drawPath(bottomPath, linePaint);
    }

    private void drawSolidPaths(Canvas canvas) {
        linePaint.setPathEffect(null);
        canvas.save();
        canvas.clipRect(solidPathClipRect);
        linePaint.setColor(topColor);
        canvas.drawPath(topPath, linePaint);
        linePaint.setColor(middleColor);
        canvas.drawPath(middlePath, linePaint);
        linePaint.setColor(bottomColor);
        canvas.drawPath(bottomPath, linePaint);
        canvas.restore();
    }

    private void drawSlide(Canvas canvas) {
        if (!needSlideBlock) {
            return;
        }
        canvas.save();
        canvas.translate(leftGap, getHeight() - bottomGap);
        canvas.save();
        canvas.translate(pointInChart.x, 0);
        //1 . 画圆形滑块
        canvas.drawPoint(0, 0, slideBlockPaint);

        //2 . 画出垂直的虚线
        linePaint.setPathEffect(slideBlockDashEffect);
        canvas.drawPath(slideBlockDashPath, linePaint);
        //3 . 如果有需要画顶部显示日期的圆角矩形
        if (needSlideDescript) {
            drawTopRoundRect(canvas);
        }
        canvas.restore();

        if (needSlideDescript){
            //4. 如果有需要在图表上画出数值
            drawValueOnLine(canvas);
        }


        canvas.restore();
    }

    private void drawValueOnLine(Canvas canvas) {
        float touchX = pointInChart.x;
        textPaint.setColor(topColor);
        float acrossY = -getAcrossY(touchX, 0);
        String value = String.valueOf(data.getTop().get(0));
        float length = textPaint.measureText(value);
        if (touchX < length){
            textPaint.setTextAlign(Paint.Align.LEFT);
        }else {
            textPaint.setTextAlign(Paint.Align.RIGHT);
        }
        canvas.drawText(value, touchX , acrossY, textPaint);
    }

    private void drawTopRoundRect(Canvas canvas) {
        RectF topRect = slideTopRect;
        Paint rectPaint = slideBlockPaint;
        Paint paint = textPaint;

        canvas.drawRoundRect(topRect, dp2px(2), dp2px(2), rectPaint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(mainBlue);
        float abs = Math.abs(topRect.right) - Math.abs(topRect.left);
        int centerX = (int) (abs / 2);
        canvas.drawText(rectDate, centerX, topRect.bottom - topRect.height() / 4, paint);
    }


    private void drawCoordinateSystem(Canvas canvas) {
        canvas.drawLine(0, 0, 0, -chartRegionHeight, baseLinePaint);
        canvas.drawLine(0, 0, chartRegionWidth, 0, baseLinePaint);
    }

    //左上方 上方的 文字描述， 抵押资产，债权增长，资金成本
    private void drawDescription(Canvas canvas) {
        Paint paint = textPaint;
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(textColor);
        Paint pointPaint = descriptionPaint;
        int capWidth = dp2px(6);
        float centerY = descriptionY;
        canvas.save();
        canvas.translate(leftGap + capWidth, dp2px(30));

        //抵押资产
        pointPaint.setColor(topColor);
        canvas.drawPoint(0, centerY, pointPaint);
        canvas.translate(capWidth, 0);

        canvas.drawText("抵押资产", 0, 0, paint);
        canvas.translate(descriptionWidth + capWidth * 2, 0);
        //债权增长
        pointPaint.setColor(middleColor);
        canvas.drawPoint(0, centerY, pointPaint);

        canvas.translate(capWidth, 0);
        canvas.drawText("债权增长", 0, 0, paint);
        canvas.translate(descriptionWidth + capWidth * 2, 0);
        //资金成本
        pointPaint.setColor(bottomColor);
        canvas.drawPoint(0, centerY, pointPaint);

        canvas.translate(capWidth, 0);
        canvas.drawText("资金成本", 0, 0, paint);

        canvas.restore();
    }

    private void drawDate(Canvas canvas) {
        canvas.save();
        canvas.translate(leftGap, getHeight() - dp2px(15));
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(startDate, 0, 0, textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.translate(chartRegionWidth, 0);
        canvas.drawText(endDate, 0, 0, textPaint);
        canvas.restore();
    }

    private float getAcrossY(float touchXInChart, int type) {
        //1. 根据 touchXInChart 找到 左边的一个 x,

        // 如果处在最右边，那么返回最后一个
        if (touchXInChart == chartRegionWidth){
            return data.getTop().get(data.getSize()-1);
        }else {
            int index = (int) (touchXInChart / cellWidth);

            float leftY = data.getTop().get(index);
            float rightY = data.getTop().get(index + 1);


            float leftX = index * cellWidth;

            float offsetY = rightY - leftY;

            float radio = (touchXInChart - leftX) / cellWidth;
            return offsetY * radio + leftY;
        }
    }
}
