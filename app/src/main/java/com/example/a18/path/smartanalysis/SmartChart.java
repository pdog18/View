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
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.a18.path.Utils.dp2px;

/**
 * 智能分析图标📈，😨
 */
public class SmartChart extends View {
    private String title = "指数走势图";

    private int leftGap = dp2px(20);
    private int bottomGap = dp2px(35);
    private int chartRegionHeight = dp2px(206);
    private float chartRegionWidth;
    private Paint baseLinePaint;
    private Paint slideBlockPaint;
    private Paint linePaint;
    private Paint descriptionPaint;
    private Paint gradePaint;
    private Paint policyPaint;

    private TextPaint textPaint;
    private TextPaint titlePaint;
    private PointF touchPointOfChart;        //这个点总是处在图表当中，不会超出图片左右两侧

    private int textColor = Color.parseColor("#e6edff");
    private int mainBlue = Color.parseColor("#5092ff");

    private int topColor = Color.parseColor("#97ed4c");
    private int middleColor = Color.parseColor("#47f8d0");
    private int bottomColor = Color.parseColor("#ffffff");
    private int[] colors = {topColor, middleColor, bottomColor};


    private float blockRadius = dp2px(16);

    private List<Path> chartPaths = new ArrayList<>();

    private Path slideBlockDashPath; //用path 而不是用drawLine 是为了方便画虚线
    private PathEffect dashChartLineEffect;
    private PathEffect slideBlockDashEffect;

    private float descriptionWidth;
    private float descriptionY;

    private boolean needSlideDescript;
    private RectF slideTopRect;

    private RectF solidPathClipRect;
    private Rect animateClipRect = new Rect(0, 0, 0, -chartRegionHeight);
    private boolean needSlideBlock = false;
    private float cellWidth;

    private int gradeRangeIndex;

    private HashMap<Integer, ValueAnimator> animatorMap;

    private int oldPage = PAGE_ASSETS;


    private static final int ANIM_EXPOSE = 0;
    private static final int ANIM_RISK_GRADE = 1;
    private static final int ANIM_POLICY = 2;
    private static final int ANIM_BOUNCE = 3;

    private static final int PAGE_ASSETS = 0;
    private static final int PAGE_RISK_GRADE = 1;
    private static final int PAGE_POLICY = 2;
    private static final int PAGE_BREAK = 3;

    public SmartChart(Context context) {
        this(context, null);
    }

    public SmartChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        initEffect();
        measureTextWidth();
        animatorMap = new HashMap<>();
    }

    private ChartDataHelper helper;
    private int dataSize;

    public void setData(ChartDataHelper helper) {
        this.helper = helper;
        this.dataSize = helper.getSize();
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
        updatePoint(event.getX(), event.getY());

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                paint.setStrokeWidth(blockRadius * 1.375f);
                needSlideDescript = true;
                checkGradeRangeChange(touchPointOfChart.x);
                return true;

            case MotionEvent.ACTION_MOVE:
                checkGradeRangeChange(touchPointOfChart.x);
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
        int curr = helper.getGradeByTouch(touchX < 0 ? 0 : touchX, cellWidth);
        if (last != curr & gradeRangeChangeListener != null) {
            gradeRangeChangeListener.onGradeRangeChange(last, curr);
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


    public void changePage(int newPage) {
        if (newPage == oldPage) {
            return;
        }
        rangeAnimation(newPage);
        oldPage = newPage;
    }

    //评级分区和策略分区的动画效果
    private void rangeAnimation(int newPage) {
        int last = oldPage;
        int curr = newPage;

        //需要隐藏动画
        if (last == PAGE_RISK_GRADE || last == PAGE_POLICY) {
            startRiskGradeRange(false, last);
        }

        // 需要显示动画，那么就显示动画
        if (curr == PAGE_RISK_GRADE || curr == PAGE_POLICY) {
            startRiskGradeRange(true, curr);
        }
    }

    public void startRiskGradeRange(boolean show, int type) {
        int time = 2000;
        Paint paint;
        ValueAnimator animator = animatorMap.get(type);
        if (type == PAGE_RISK_GRADE) {
            paint = gradePaint;
        } else {
            paint = policyPaint;
        }
        int currAlpha = paint.getAlpha();

        if (animator != null && animator.isRunning()) {
            animator.cancel();
            //修正接下来的动画时长
            time = (int) (animator.getAnimatedFraction() * 2000);
            currAlpha = paint.getAlpha();
            animatorMap.remove(type);
        }

        ValueAnimator valueAnimator = getValueAnimator(show, time, paint, currAlpha);
        if (type == PAGE_RISK_GRADE) {
            animatorMap.put(ANIM_RISK_GRADE, valueAnimator);
        } else {
            animatorMap.put(ANIM_POLICY, valueAnimator);
        }

        valueAnimator.start();
    }

    @NonNull
    private ValueAnimator getValueAnimator(boolean show, int time, Paint paint, int currAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(1)
                .setDuration(time);
        int finalCurrAlpha = currAlpha;
        Paint finalPaint = paint;
        animator.addUpdateListener(animation -> {
            if (!show && finalCurrAlpha == 0) {       //需要逐渐透明，并且当前已经透明
                return;
            } else if (show && finalCurrAlpha == 255) {//需要逐渐显示，并且当前已经完全显示
                return;
            }

            float rate = (animation.getAnimatedFraction());
            switch (finalCurrAlpha) {
                case 0:
                    finalPaint.setAlpha((int) (255 * rate));
                    break;
                case 255:
                    finalPaint.setAlpha((int) (255 - 255 * rate));
                    break;
                default:
                    if (!show) {
                        //finalCurr -> 0
                        float v1 = (finalCurrAlpha) * (1.0f - rate);
                        finalPaint.setAlpha((int) v1);
                    } else {
                        // finalCurr -> 255
                        float v1 = (255 - finalCurrAlpha) * rate + finalCurrAlpha;
                        finalPaint.setAlpha((int) v1);
                    }
                    break;
            }
            SmartChart.this.invalidate();
        });
        return animator;
    }

    @Override
    protected void onDetachedFromWindow() {
        for (ValueAnimator animator : animatorMap.values()) {
            animator.cancel();
        }
        animatorMap.clear();
        animatorMap = null;
        super.onDetachedFromWindow();
    }

    public void exposeChart() {
        ValueAnimator exposeAnimation = ValueAnimator.ofFloat(1.0f);
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

        animatorMap.put(ANIM_EXPOSE, exposeAnimation);
        exposeAnimation.start();

    }

    //slideblock 弹动
    private void bounceSlideBlock() {
        needSlideBlock = true;
        touchPointOfChart = new PointF(chartRegionWidth, 0);

        ValueAnimator bounceAnimation = ValueAnimator.ofFloat(1.0f);
        bounceAnimation.setDuration(2000);
        bounceAnimation.setInterpolator(new OvershootInterpolator(3));
        bounceAnimation.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            float radius = blockRadius * v;
            slideBlockPaint.setStrokeWidth(radius);
            invalidate();
        });

        animatorMap.put(ANIM_BOUNCE, bounceAnimation);
        bounceAnimation.start();
    }

    private void createPaths() {
        cellWidth = chartRegionWidth / (dataSize - 1);

        if (cellWidth == 0) {
            return;
        }

        chartPaths.add(helper.buildPath(helper.getLists(0), cellWidth, chartRegionHeight));       //抵押资产
        chartPaths.add(helper.buildPath(helper.getLists(1), cellWidth, chartRegionHeight));       //债权
        chartPaths.add(helper.buildPath(helper.getLists(2), cellWidth, chartRegionHeight));       //资金成本
    }

    private void initPoint() {
        slideBlockDashPath = new Path();
        slideTopRect = new RectF();
    }


    private void updatePoint(float x, float y) {
        float pointX;
        if (x < leftGap) {      //超出图表左侧
            pointX = 0;
        } else if (x > leftGap + chartRegionWidth) {    // 超出图表右侧
            pointX = chartRegionWidth;
        } else {
            pointX = x - leftGap;
        }
        touchPointOfChart.set(pointX, y);
        slideBlockDashPath.reset();
        slideBlockDashPath.moveTo(0, 0);
        slideBlockDashPath.rLineTo(0, -chartRegionHeight);

        updateRoundRect(touchPointOfChart.x);

        solidPathClipRect.right = touchPointOfChart.x;
    }

    private void updateRoundRect(float x) {
        //顶部的 圆角矩形内容会发生改变
        String dateAtIndex = helper.getDateAtIndex(x, cellWidth);
        float textWidth = textPaint.measureText(dateAtIndex) + dp2px(10);
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

        policyPaint = new Paint(gradePaint);


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

        if (helper == null) {
            return;
        }
        canvas.clipRect(animateClipRect);
        drawDashPaths(canvas);
        drawSolidPaths(canvas);

        drawRiskGradeRange(canvas, helper.getRiskGradeMap(), gradePaint);
        drawPolicyRange(canvas, helper.getPolicyMap(), policyPaint);
        canvas.restoreToCount(save);

        drawDate(canvas);
        drawSlide(canvas);
    }

    private void drawRiskGradeRange(Canvas canvas, List<ChartDataHelper.Range> ranges, Paint paint) {
        drawRange(canvas, ranges, paint);
    }

    private void drawPolicyRange(Canvas canvas, List<ChartDataHelper.Range> policyMap, Paint paint) {
        drawRange(canvas, policyMap, paint);
    }

    private void drawRange(Canvas canvas, List<ChartDataHelper.Range> ranges, Paint paint) {
        int height = getHeight();

        for (ChartDataHelper.Range range : ranges) {
            int endIndex = range.endIndex;
            canvas.drawLine(endIndex * cellWidth, 0, endIndex * cellWidth, -height, paint);
        }

        for (ChartDataHelper.Range range : ranges) {
            int endIndex = range.endIndex;
            int startIndex = range.startIndex;
            float centerX = (endIndex * cellWidth - startIndex * cellWidth) / 2 + startIndex * cellWidth;

            canvas.drawText(String.valueOf(range.grade), centerX, 0, paint);
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
        canvas.translate(touchPointOfChart.x, 0);
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
        float touchX = touchPointOfChart.x;
        int index = getIndex(touchX);

        for (int i = 0; i < 3; i++) {
            textPaint.setColor(colors[i]);
            String value = String.valueOf(helper.getLists(i)[index]);
            float length = textPaint.measureText(value);

            if (touchX < length) {
                textPaint.setTextAlign(Paint.Align.LEFT);
            } else {
                textPaint.setTextAlign(Paint.Align.RIGHT);
            }
            float acrossY = helper.getHeightOnPath(touchX, cellWidth, i, chartRegionHeight);
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
        canvas.drawText(helper.getDateAtIndex(touchPointOfChart.x, cellWidth), centerX, topRect.bottom - topRect.height() / 4, paint);
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
        canvas.drawText(helper.getStartDate(), 0, 0, textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.translate(chartRegionWidth, 0);
        canvas.drawText(helper.getEndDate(), 0, 0, textPaint);
        canvas.restore();
    }


    private int getIndex(float touchXInChart) {
        return (int) (touchXInChart / cellWidth);
    }


    private GradeRangeChangeListener gradeRangeChangeListener;

    public void setGradeRangeChangeListener(GradeRangeChangeListener listener) {
        this.gradeRangeChangeListener = listener;
    }

    public interface GradeRangeChangeListener {
        void onGradeRangeChange(int last, int curr);
    }

    public int getLeftGap() {
        return leftGap;
    }
}
