package com.example.a18.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * desc: spider web
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/5  11 :26
 */

public class SpiderWebView extends View {

    //六边形最外侧的六个顶点的坐标
    float[][] points = new float[FLAG][2];
    float[][] progressPoints = null;

    static int FLAG = 6;


    //记录从中心原点到六个顶点的pathmeasure，用作百分比获取坐标使用
    PathMeasure[] mPathMeasures = new PathMeasure[FLAG];


    Paint mPaint;
    Paint bluePaint;
    Paint greenPaint;

    private Path mPath;

    public SpiderWebView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private static final String TAG = "WebView";

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(3);


        bluePaint = new Paint();
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setColor(0x440000ff);

        greenPaint = new Paint();
        greenPaint.setStyle(Paint.Style.FILL);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(10);

        mPath = createPath(80, 6);
    }

    private Path createPath(float bias, int count) {
        Path path = new Path();
        float angle = 360 / FLAG;

        //画出count 个六边形
        while (count > 0) {
            float side = bias * count;
            count--;

            float height = (float) (side * Math.sin(Math.PI * angle / 180));
            //移动到y轴上某一点
            path.moveTo(side, 0);

            // TODO: 2017/11/6 动态计算各个角度应该划线的方向以及长度
            //向指定方向划线
            if (angle >= 0 && angle <= 90) {
                path.rLineTo(-side / 2, -height);
            } else {
                path.rLineTo(-side / 2, -height);
            }
            path.rLineTo(-side, 0);
            path.rLineTo(-side / 2, height);
            path.rLineTo(side / 2, height);
            path.rLineTo(side, 0);
            path.rLineTo(side / 2, -height);
        }

        //计算六个顶点坐标，将中心点与顶点连接
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();

        for (int i = 0; i < FLAG; i++) {
            float[] point = new float[2];
            pathMeasure.getPosTan(length / FLAG * i, point, null);
            points[i] = point;
        }


        for (int i = 0; i < FLAG; i++) {
            Path line = new Path();
            line.moveTo(0, 0);
            line.lineTo(points[i][0], points[i][1]);
            path.addPath(line);

            PathMeasure pathMeasure1 = new PathMeasure(line, false);
            mPathMeasures[i] = pathMeasure1;
        }

        return path;
    }


    /**
     * 设置六个维度的进度
     * 传入的数组长度必须为6
     * 传入的数组每个数值的取值范围x :  0 < x < 1
     *
     * @param progress
     */
    public void setProgress(float... progress) {
        if (progress.length != FLAG) {
            throw new IllegalArgumentException("progress.length must be 6 , " + progress.length);
        }

        progressPoints = new float[FLAG][2];

        for (int i = 0; i < FLAG; i++) {
            PathMeasure pathMeasure = mPathMeasures[i];
            pathMeasure.getPosTan(pathMeasure.getLength() * progress[i], progressPoints[i], null);
        }

        invalidate();
    }

    private Path getProgressPath() {
        Path path = new Path();

        for (int i = 0; i < FLAG; i++) {
            if (i == 0) {
                path.moveTo(progressPoints[i][0], progressPoints[i][1]);
            } else {
                path.lineTo(progressPoints[i][0], progressPoints[i][1]);
            }
        }
        path.close();
        return path;
    }

    private boolean haveProgress() {
        return progressPoints != null;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() >> 1, getHeight() >> 1);

        canvas.drawPath(mPath, mPaint);
        if (haveProgress()) {
            canvas.drawPath(getProgressPath(), bluePaint);

            //绘制进度的顶点
            for (int i = 0; i < FLAG; i++) {
                canvas.drawCircle(progressPoints[i][0], progressPoints[i][1], 10, greenPaint);
            }
        }
    }
}
