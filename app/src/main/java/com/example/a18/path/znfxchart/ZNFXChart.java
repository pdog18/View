package com.example.a18.path.znfxchart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by machao on 2017/11/20.
 */

public class ZNFXChart extends View {

    boolean DEBUG = true;


    List<Path> paths = new ArrayList<>();
    List<Paint> paints = new ArrayList<>();
    Paint baseLinePaint ;
    RectF clipRect ;
    float[][] floats;

    int lineLength = 3;


    //峰谷差值 ，默认为控件高度
    float diffHeight;
    float diffWidth;

    float peak; // 峰
    float valley;   //谷

    ObjectAnimator animator = ObjectAnimator.ofFloat(this,"progress",1);

    public ZNFXChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        floats = new float[lineLength][];
    }


    private void initPaint() {
        for (int i = 0; i < lineLength; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.STROKE);
            paints.add(paint);
            paths.add(new Path());
        }

        baseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        baseLinePaint.setStrokeWidth(2);
        baseLinePaint.setColor(Color.GRAY);
        baseLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //记录图表宽高
        diffHeight = h;
        diffWidth = w;

        clipRect = new RectF(0,0,0,h);


        setPaths();
    }

    private void setPaths() {

        for (int i = 0; i < floats.length; i++) {
            if (floats[i] ==null) {
                return;
            }

            Path path = paths.get(i);

            path.reset();


            float y1 = lineHeight(floats[i][0]);

            path.moveTo(0, y1);

            for (int j = 1; j < floats[i].length; j++ ) {
                //算出坐标实际所在的坐标
                float y2 = lineHeight(floats[i][j]);
                float x2 = diffWidth/floats[i].length * j ;
                path.lineTo(x2, y2);
            }
        }
    }

    public void startAnim(){
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    private void setProgress(float progress){
        // FIXME: 2017/11/20 处理动画开始时 clipRect为空的情况
        if (clipRect == null){
            return;
        }
        clipRect.right = diffWidth * progress;
        invalidate();
    }


    public ZNFXChart setPaintColor(int index, @ColorInt int color) {
        paints.get(index).setColor(color);
        return this;
    }
    public ZNFXChart setPaintWidth(int index, int width) {
        paints.get(index).setStrokeWidth(width);
        return this;
    }

    //提供给外界设置y轴坐标的path
    public ZNFXChart addPath(int index, float[] points) {
        floats[index] = points;
        requestLayout();
        return this;
    }

    //计算当前点的高度
    private float lineHeight(float point) {

        //1. 最高点减去最低点
        float diff = peak - valley;
        //2. 高度的区域占总区域的高度
        float topPadding = peak - point;
        //3. 当前高度所占百分比
        return topPadding / diff * diffHeight;
    }

    //设置图表的峰谷
    public ZNFXChart setPeakAndValley(float peak, float valley){
        this.peak = peak;
        this.valley = valley;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制灰色的背景基准线
        drawBaseLine(canvas);

        //绘制图表
        drawChartLine(canvas);
    }

    private void drawChartLine(Canvas canvas) {
        canvas.save();
        canvas.clipRect(clipRect);
        for (int i = 0; i < lineLength; i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
        canvas.restore();
    }

    private void drawBaseLine(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(0,diffHeight/5f*i,diffWidth,diffHeight/5f*i,baseLinePaint);
        }

    }
}
