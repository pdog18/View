package com.example.a18.path.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/10  11 :11
 */

public class Practice11PieChartView extends View {
    private static final String TAG = "Practice11PieChartView";

    Paint paintArc = new Paint();//扇形画笔
    Paint mPaint = new Paint();//指示线画笔
    Paint textPaint = new Paint();//文字画笔
    RectF rectF = new RectF();

    int textSize = 30;
    int height = 0;

    Point centerPoint = new Point();//圆心
    float r;//半径


    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight() - 300;

        int left = 200;
        int top = 50;
        int right = left + height;
        int bottom = top + height;

        rectF = new RectF(left, top, right, bottom);
        results.add(new Result(180, 120, "#F44336", "Lollipop").setNeedMove(true));
        results.add(new Result(300, 60, "#FFC107", "Marshmallow"));
        results.add(new Result(3, 7, "#9C27B0", "Gingerbread"));
        results.add(new Result(13, 20, "#9E9E9E", "Ice Cream Sandwich"));
        results.add(new Result(36, 40, "#009688", "Jelly Bean"));
        results.add(new Result(80, 98, "#2196F3", "KitKat"));
        centerPoint.x = (right - left) / 2 + left;
        centerPoint.y = (bottom - top) / 2 + top;
        r = (right - left) / 2;

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
    }


    ArrayList<Result> results = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Result result : results) {
            //画扇形
            drawArc(canvas, result);

            //画指示线
            drawPath(canvas, result);

            //是否需要离开圆心一些距离
            drawGap(canvas, result);
        }
    }

    private void drawGap(Canvas canvas, Result result) {
        if (result.needMove) {
            canvas.translate(10, 20);
        }
    }

    private void drawPath(Canvas canvas, Result result) {
        int x = (int) (centerPoint.x + r * Math.cos(result.getHalfAngle() * Math.PI / 180));
        int y = (int) (centerPoint.y + r * Math.sin(result.getHalfAngle() * Math.PI / 180));
        //判断指示线方向

        int[] type = getDirectionArray(x, y);

        Path path = new Path();
        path.moveTo(x, y);
        path.rLineTo(type[2], type[3]);
        path.rLineTo(type[4], type[5]);
        switch (type[0]) {
            case LEFT_TOP:
            case LEFT_BOTTOM:
                textPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            case RIGHT_TOP:
            case RIGHT_BOTTOM:
                textPaint.setTextAlign(Paint.Align.LEFT);
                break;
        }
        canvas.drawPath(path, mPaint);

        int textX = type[2] + type[4] + x + type[1];

        int textY = type[3] + type[5] + y + textSize / 3;

        canvas.drawText(result.name, textX, textY, textPaint);

    }

    private int[] getDirectionArray(int x, int y) {
        int[] arr = new int[6];
        if (x < centerPoint.x) {
            arr[1] = arr[2] = -20;
            arr[4] = -100;
            arr[5] = 0;
            if (y < centerPoint.y) {//左上
                arr[0] = LEFT_TOP;
                arr[3] = -20;
            } else {//左下
                arr[0] = LEFT_BOTTOM;
                arr[3] = 20;
            }
        } else {
            arr[1] = arr[2] = 20;
            arr[4] = 100;
            arr[5] = 0;
            if (y < centerPoint.y) {//右上
                arr[0] = RIGHT_TOP;
                arr[3] = -20;
            } else {//右下
                arr[0] = RIGHT_BOTTOM;
                arr[3] = 20;
            }
        }
        return arr;
    }

    // TODO: 2017/7/13  防止重叠

    final int LEFT_TOP = 0;
    final int LEFT_BOTTOM = 1;
    final int RIGHT_TOP = 2;
    final int RIGHT_BOTTOM = 3;


    private void drawArc(Canvas canvas, Result result) {
        paintArc.setColor(result.color);
        canvas.drawArc(rectF, result.startAngle, result.sweepAngle, true, paintArc);
    }

    private static class Result {
        int startAngle;
        int sweepAngle;
        int color;
        boolean needMove;
        String name;

        Result(int startAngle, int sweepAngle, String color, String name) {
            this.startAngle = startAngle;
            this.sweepAngle = sweepAngle;
            this.color = Color.parseColor(color);
            this.name = name;
        }

        float getHalfAngle() {
            return sweepAngle / 2 + startAngle;
        }

        Result setNeedMove(boolean needMove) {
            this.needMove = needMove;
            return this;
        }
    }
}