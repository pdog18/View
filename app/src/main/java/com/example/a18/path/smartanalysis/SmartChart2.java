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

import java.util.ArrayList;
import java.util.List;

import static com.example.a18.path.Utils.dp2px;

/**
 * 智能分析图标📈，😨
 */
public class SmartChart2 extends View {

    private String startDate = "2014/08/09";
    private String endDate = "2017/12/19";
    private String rectDate = "2015/05/12";

    private String title = "指数走势图";

    int leftGap = dp2px(20);
    int bottomGap = dp2px(35);
    int chartRegionHeight = dp2px(206);
    float chartRegionWidth;
    private Paint baseLinePaint;
    private Paint slideBlockPaint;
    private Paint linePaint;
    private Paint descriptionPaint;
    private Paint gradePaint;
    private TextPaint textPaint;
    private TextPaint titlePaint;
    private PointF pointInChart;        //这个点总是处在图表当中，不会超出图片左右两侧

    int textColor = Color.parseColor("#e6edff");
    int mainBlue = Color.parseColor("#5092ff");

    int topColor = Color.parseColor("#97ed4c");
    int middleColor = Color.parseColor("#47f8d0");
    int bottomColor = Color.parseColor("#ffffff");
    int[] colors = {topColor, middleColor, bottomColor};


    private float blockRadius = dp2px(16);

    private List<Path> chartPaths = new ArrayList<>();

    private Path slideBlockDashPath; //用path 而不是用drawLine 是为了方便画虚线
    private PathEffect dashChartLineEffect;
    private PathEffect slideBlockDashEffect;


    float descriptionWidth;
    float descriptionY;

    boolean needSlideDescript;
    private RectF slideTopRect;

    private RectF solidPathClipRect;
    private Rect animateClipRect = new Rect(0, 0, 0, -chartRegionHeight);
    private ValueAnimator exposeAnimation;
    private boolean needSlideBlock = false;

    float cellWidth;
    private ValueAnimator gradeRangeAnimation;
    private List<MockSmartAnalysis.GradeRange> gradeRange;
    private int gradeRangeIndex;


    public SmartChart2(Context context) {
        this(context, null);
    }

    public SmartChart2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartChart2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        initEffect();
        measureTextWidth();


    }

    MockSmartAnalysis data;
    int dataSize;

    public void setData(MockSmartAnalysis data) {
        this.data = data;
        this.dataSize = data.getXList().size();
        gradeRange = data.getGradeRange();
        gradeRangeIndex = data.getGradeRange().size() - 1;

        createPaths();
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
                checkGradeRangeChange(event.getX() - leftGap);
                return true;

            case MotionEvent.ACTION_MOVE:
                updatePoint(((int) event.getX()), ((int) event.getY()));
                checkGradeRangeChange(event.getX() - leftGap);
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

    private void checkGradeRangeChange(float touchX) {

        int last = gradeRangeIndex;
        int curr  = data.getIndex(cellWidth, touchX < 0 ? 0: (int) touchX, gradeRange);
        if (last != curr & gradeRangeChangeListener != null) {
            gradeRangeChangeListener.onGradeRangeChange(last,curr);
        }
        gradeRangeIndex = curr;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chartRegionWidth = w - leftGap * 2;
        initPoint();
        solidPathClipRect = new RectF(0, 0, w, -h);
        createPaths();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        exposeChart();
    }

    public void startGradeRange(boolean reverse) {

        int currAlpha = gradePaint.getAlpha();
        int time = 2000;
        if (gradeRangeAnimation != null && gradeRangeAnimation.isRunning()) {
            gradeRangeAnimation.cancel();
            //修正接下来的动画时长
            time = (int) (gradeRangeAnimation.getAnimatedFraction() * 2000);
            gradeRangeAnimation = null;
            currAlpha = gradePaint.getAlpha();
        }

        gradeRangeAnimation = ValueAnimator.ofFloat(1)
                .setDuration(time);
        int finalCurrAlpha = currAlpha;
        gradeRangeAnimation.addUpdateListener(animation -> {
            if (reverse && finalCurrAlpha == 0) {       //需要逐渐透明，并且当前已经透明
                return;
            } else if (!reverse && finalCurrAlpha == 255) {//需要逐渐显示，并且当前已经完全显示
                return;
            }

            float rate = (animation.getAnimatedFraction());
            switch (finalCurrAlpha) {
                case 0:
                    gradePaint.setAlpha((int) (255 * rate));
                    break;
                case 255:
                    gradePaint.setAlpha((int) (255 - 255 * rate));
                    break;
                default:
                    if (reverse) {
                        //finalCurr -> 0
                        float v1 = (finalCurrAlpha) * (1.0f - rate);
                        gradePaint.setAlpha((int) v1);
                    } else {
                        // finalCurr -> 255
                        float v1 = (255 - finalCurrAlpha) * rate + finalCurrAlpha;
                        gradePaint.setAlpha((int) v1);
                    }
                    break;
            }
            SmartChart2.this.invalidate();
        });
        gradeRangeAnimation.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (exposeAnimation != null) {
            exposeAnimation.cancel();
            exposeAnimation = null;
        }

        if (gradeRangeAnimation != null) {
            gradeRangeAnimation.cancel();
            gradeRangeAnimation = null;
        }
        super.onDetachedFromWindow();
    }

    public void exposeChart() {
        exposeAnimation = ValueAnimator.ofFloat(1.0f);
        exposeAnimation.setDuration(2000);
        exposeAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        exposeAnimation.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            animateClipRect.right = (int) (v * chartRegionWidth);
            invalidate();
        });
        exposeAnimation.addListener(new SimpleAnimator() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bounceSlideBlock();
            }
        });
        exposeAnimation.start();
    }

    //slideblock 弹动
    private void bounceSlideBlock() {
        needSlideBlock = true;
        pointInChart = new PointF(chartRegionWidth, 0);

        exposeAnimation.removeAllListeners();
        exposeAnimation.removeAllUpdateListeners();
        exposeAnimation = exposeAnimation.clone();
        exposeAnimation.setInterpolator(new OvershootInterpolator(3));
        exposeAnimation.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            float radius = blockRadius * v;
            slideBlockPaint.setStrokeWidth(radius);
            invalidate();
        });
        exposeAnimation.start();
    }


    private void createPaths() {
        cellWidth = chartRegionWidth / (dataSize - 1);

        chartPaths.clear();
        chartPaths.add(buildPath(data.getAssetValueIndexList()));       //抵押资产
        chartPaths.add(buildPath(data.getDebetIndexList()));            //债权
        chartPaths.add(buildPath(data.getAssetCostIndexList()));        //资金成本
    }

    private Path buildPath(List<Double> list) {
        Path path = new Path();

        path.moveTo(0, valueMap(list.get(0)));

        for (int i = 1; i < list.size(); i++) {
            path.lineTo(cellWidth * i, valueMap(list.get(i)));
        }
        return path;
    }

    private int valueMap(double before) {
        double topmost = data.getTopmost();

        return (int) (-before * (chartRegionHeight * 1.0f / topmost));
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
            pointX = chartRegionWidth;
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
            offset = chartRegionWidth - (x + textWidth / 2);
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
        slideBlockPaint.setShadowLayer(dp2px(2), dp2px(2), dp2px(2), Color.BLACK);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2px(2));
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);

        descriptionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        descriptionPaint.setStrokeWidth(dp2px(6));
        descriptionPaint.setStrokeCap(Paint.Cap.ROUND);
        descriptionPaint.setStyle(Paint.Style.STROKE);

        gradePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gradePaint.setStrokeWidth(dp2px(1));
        gradePaint.setColor(Color.WHITE);
        gradePaint.setAlpha(0);
        gradePaint.setTextAlign(Paint.Align.CENTER);
        gradePaint.setTextSize(dp2px(10));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(dp2px(12));
        textPaint.setColor(textColor);

        titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextSize(dp2px(15));
        titlePaint.setColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!needSlideDescript) {
            drawDescription(canvas);
        }


        drawTitle(canvas);

        int save = canvas.save();
        canvas.translate(leftGap, getHeight() - bottomGap);
        drawCoordinateSystem(canvas);

        if (data == null) {
            return;
        }
        canvas.clipRect(animateClipRect);
        drawDashPaths(canvas);
        drawSolidPaths(canvas);

        drawGradeRange(canvas);
        canvas.restoreToCount(save);

        drawDate(canvas);
        drawSlide(canvas);
    }

    private void drawGradeRange(Canvas canvas) {
        int size = gradeRange.size();
        int height = getHeight();

        for (int i = 0; i < size; i++) {
            int endIndex = gradeRange.get(i).endIndex;
            canvas.drawLine(endIndex * cellWidth, 0, endIndex * cellWidth, -height, gradePaint);
            textPaint.setTextAlign(Paint.Align.CENTER);
        }
        for (int i = 0; i < size; i++) {
            int endIndex = gradeRange.get(i).endIndex;
            int startIndex = gradeRange.get(i).startIndex;
            float centerX = (endIndex * cellWidth - startIndex * cellWidth) / 2 + startIndex * cellWidth;

            canvas.drawText(gradeRange.get(i).risk, centerX, 0, gradePaint);
        }
    }

    private void drawTitle(Canvas canvas) {
        canvas.drawText(title, leftGap, dp2px(25), titlePaint);
    }


    private void drawDashPaths(Canvas canvas) {
        linePaint.setPathEffect(dashChartLineEffect);
        for (int i = 0; i < 3; i++) {
            linePaint.setColor(colors[i]);
            canvas.drawPath(chartPaths.get(i), linePaint);
        }
    }

    private void drawSolidPaths(Canvas canvas) {
        linePaint.setPathEffect(null);
        canvas.save();
        canvas.clipRect(solidPathClipRect);
        for (int i = 0; i < 3; i++) {
            linePaint.setColor(colors[i]);
            canvas.drawPath(chartPaths.get(i), linePaint);
        }
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

        if (needSlideDescript) {
            //4. 如果有需要在图表上画出数值
            drawValueOnLine(canvas);
        }

        canvas.restore();
    }

    private void drawValueOnLine(Canvas canvas) {
        float touchX = pointInChart.x;
        int index = getIndex(touchX);

        for (int i = 0; i < 3; i++) {
            textPaint.setColor(colors[i]);
            String value = getListAtIndex(i).get(index).toString();
            float length = textPaint.measureText(value);

            if (touchX < length) {
                textPaint.setTextAlign(Paint.Align.LEFT);
            } else {
                textPaint.setTextAlign(Paint.Align.RIGHT);
            }
            float acrossY = getAcrossY(touchX, i);
            canvas.drawText(value, touchX, acrossY, textPaint);
        }
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
        canvas.translate(leftGap + capWidth, dp2px(49));

        //抵押资产
        pointPaint.setColor(colors[0]);
        canvas.drawPoint(0, centerY, pointPaint);
        canvas.translate(capWidth, 0);

        canvas.drawText("抵押资产", 0, 0, paint);
        canvas.translate(descriptionWidth + capWidth * 2, 0);
        //债权增长
        pointPaint.setColor(colors[1]);
        canvas.drawPoint(0, centerY, pointPaint);

        canvas.translate(capWidth, 0);
        canvas.drawText("债权增长", 0, 0, paint);
        canvas.translate(descriptionWidth + capWidth * 2, 0);
        //资金成本
        pointPaint.setColor(colors[2]);
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
        List<Double> list = getListAtIndex(type);

        // 如果处在最右边，那么返回最后一个坐标的高度
        if (touchXInChart == chartRegionWidth) {
            return valueMap(list.get(dataSize - 1));
        } else {
            int index = getIndex(touchXInChart);

            double leftY = list.get(index);
            double rightY = list.get(index + 1);


            float leftX = index * cellWidth;

            double offsetY = rightY - leftY;

            float radio = (touchXInChart - leftX) / cellWidth;
            return valueMap((int) (offsetY * radio + leftY));
        }
    }

    private int getIndex(float touchXInChart) {
        return (int) (touchXInChart / cellWidth);
    }

    private List<Double> getListAtIndex(int index) {
        if (index == 0) {
            return data.getAssetValueIndexList();
        }
        if (index == 1) {
            return data.getDebetIndexList();
        } else {

            return data.getAssetCostIndexList();
        }
    }

    GradeRangeChangeListener gradeRangeChangeListener;

    public void setGradeRangeChangeListener(GradeRangeChangeListener listener) {
        this.gradeRangeChangeListener = listener;
    }
    public interface GradeRangeChangeListener{
        void onGradeRangeChange(int last, int curr);
    }
}
