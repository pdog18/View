package com.example.a18.path.smartanalysis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.example.a18.path.Utils.dp2px;

/**
 * 智能分析图标📈，😨
 */

public class SmartChart extends View {
    int leftGap = dp2px(20);
    int bottomGap = dp2px(35);
    int chartRegionHeight = dp2px(206);
    int chartRegionWidth;
    private Paint baseLinePaint;
    private Paint slideBlockPaint;
    private Paint linePaint;
    private Paint descriptionPaint;
    private TextPaint textPaint;
    private Point point;

    int topColor = Color.parseColor("#97ed4c");
    int middleColor = Color.parseColor("#47f8d0");
    int bottomColor = Color.parseColor("#ffffff");
    int textColor = Color.parseColor("#e6edff");
    int mainBlue = Color.parseColor("#5092ff");

    private int blockRadius = dp2px(16);

    private Path topPath;
    private Path middlePath;
    private Path bottomPath;

    private Path slideBlockDashPath; //用path 而不是用drawLine 是为了方便化虚线
    private PathEffect dashChartLineEffect;
    private PathEffect slideBlockDashEffect;

    private Rect solidPathClipRect;

    float descriptionWidth;
    float descriptionY;
    private RectF slideTopRect;

    boolean needSlideDescript;


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Paint piant = slideBlockPaint;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                piant.setStrokeWidth(blockRadius * 1.375f);
                needSlideDescript = true;

                return true;

            case MotionEvent.ACTION_MOVE:
                updatePoint(((int) event.getX()), ((int) event.getY()));

                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                piant.setStrokeWidth(blockRadius);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        chartRegionWidth = w - leftGap * 2;
        initPoint();

        createPaths();
        solidPathClipRect = new Rect(0, 0, w, -h);

    }

    private void createPaths() {
        createTopPath();
        createMiddlePath();
        createBottomPath();
    }

    private void createTopPath() {
        Path path = new Path();
        path.moveTo(0, -300);
        path.lineTo(chartRegionWidth, -600);
        topPath = path;
    }

    private void createMiddlePath() {
        Path path = new Path();
        path.moveTo(0, -200);
        path.lineTo(chartRegionWidth, -400);
        middlePath = path;

    }

    private void createBottomPath() {
        Path path = new Path();
        path.moveTo(0, -100);
        path.lineTo(chartRegionWidth, -200);
        bottomPath = path;

    }

    private void initPoint() {
        point = new Point(chartRegionWidth + leftGap, 0);
        slideBlockDashPath = new Path();
        slideTopRect = new RectF();
    }


    private void updatePoint(int x, int y) {
        int pointX;
        if (x < leftGap) {      //超出图表左侧
            pointX = leftGap;
        } else if (x > leftGap + chartRegionWidth) {    // 超出图表右侧
            pointX = chartRegionWidth + leftGap;
        } else {
            pointX = x;
        }
        point.set(pointX, y);
        slideBlockDashPath.reset();
        slideBlockDashPath.moveTo(0, 0);
        slideBlockDashPath.rLineTo(0, -chartRegionHeight);

        //顶部的 圆角矩形内容会发生改变
        float textWidth = textPaint.measureText(rectDate);
        int top = -(chartRegionHeight + dp2px(30));
        slideTopRect.set(
                -(textWidth / 2  + dp2px(5)),
                top,
                textWidth / 2 + dp2px(5),
                top + dp2px(18));

        solidPathClipRect.right = point.x - leftGap;

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

        drawDescription(canvas);

        int save = canvas.save();
        canvas.translate(leftGap, getHeight() - bottomGap);
        drawCoordinateSystem(canvas);


        drawDashPaths(canvas);

        drawSolidPaths(canvas);

        canvas.restoreToCount(save);

        drawDate(canvas);

        drawSlide(canvas);

    }

    private String statrDate = "2014/08/09";
    private String endDate = "2017/12/19";
    private String rectDate = "2015/05/12";




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
        canvas.save();
        canvas.translate(0, getHeight() - bottomGap);
        canvas.translate(point.x,0);
        //1 . 画圆形滑块
        canvas.drawPoint(0, 0, slideBlockPaint);

        //2 . 如果有需要画出虚线 ，和顶部显示日期的圆角矩形
        if (needSlideDescript) {
            // 2.1 画出垂直的虚线
            linePaint.setPathEffect(slideBlockDashEffect);
            canvas.drawPath(slideBlockDashPath, linePaint);
            // 2.2 画出顶部圆角矩形及文字
            drawRectText(canvas);
        }

        canvas.restore();
    }

    private void drawRectText(Canvas canvas) {
        canvas.drawRoundRect(slideTopRect, 5, 5, slideBlockPaint);
        Paint paint = textPaint;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(mainBlue);
        canvas.drawText(rectDate,0,slideTopRect.bottom,paint);
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
        canvas.drawText(statrDate, 0, 0, textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.translate(chartRegionWidth, 0);
        canvas.drawText(endDate, 0, 0, textPaint);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.restore();

    }
}
