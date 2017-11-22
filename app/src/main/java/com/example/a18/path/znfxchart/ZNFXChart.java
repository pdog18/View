package com.example.a18.path.znfxchart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能分析图表
 */
public class ZNFXChart extends View {
    private static final String TAG = "ZNFXChart";

    boolean DEBUG = true;
    List<KLine> kLines = new ArrayList<>();

    Paint baseLinePaint ;
    RectF clipRect= new RectF();

    float peak; // 峰
    float valley;   //谷


    boolean pathCreated =false;
    ObjectAnimator animator = ObjectAnimator.ofFloat(this,"progress",1);

    public ZNFXChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);//关闭硬件加速

        baseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        baseLinePaint.setStrokeWidth(2);
        baseLinePaint.setColor(Color.GRAY);
        baseLinePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 创建图表的线
     */
    private void createKLinePath(){
        if (getWidth() ==0){
            Log.e(TAG, "createKLinePath: getWidth == 0 " );
            return;
        }
        for (KLine kLine : kLines) {
            Path path = kLine.path;
            float startY = lineHeight(kLine.points[0]);

            path.moveTo(0,startY);
            for (int i = 1; i < kLine.points.length; i++) {
                float y2 = lineHeight(kLine.points[i]);
                float x2 = getWidth()/kLine.points.length * i ;
                path.lineTo(x2, y2);
            }
            kLine.path  = path;
        }
        Log.e(TAG, "createKLinePath: 创建 Path ,创建数量" + kLines.size());
        pathCreated = true;
    }

    public void startAnim(){

        post(new Runnable() {
            @Override
            public void run() {

                if (!pathCreated) {
                    Log.e(TAG, "startAnim:  Path 未创建" );
                    createKLinePath();
                }
                animator.setDuration(2000);
                animator.setInterpolator(new LinearInterpolator());
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.start();
            }
        });

    }

    private void setProgress(float progress){
        clipRect.right = getWidth() * progress;
        clipRect.bottom = getHeight();
        invalidate();
    }

    public ZNFXChart addKLine(@NonNull KLine kLine){
        kLines.add(kLine);
        return this;
    }

    //计算当前点的高度
    private float lineHeight(float point) {
        //1. 最高点减去最低点
        float diff = peak - valley;
        //2. 高度的区域占总区域的高度
        float topPadding = peak - point;
        //3. 当前高度所占百分比
        return topPadding / diff * getHeight();
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
        for (int i = 0; i < kLines.size(); i++) {
            canvas.drawPath(kLines.get(i).path, kLines.get(i).paint);
        }
        canvas.restore();
    }

    private void drawBaseLine(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(0,getHeight()/5f*i,getWidth(),getHeight()/5f*i,baseLinePaint);
        }
    }
}
