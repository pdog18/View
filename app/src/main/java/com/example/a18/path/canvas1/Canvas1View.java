package com.example.a18.path.canvas1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/10  11 :23
 */

public class Canvas1View extends View {

    private Paint mPaint;

    private Picture mPicture = new Picture();

    // 2.录制内容方法
    private void recording() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(10, 10);
        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // 在Canvas中具体操作
        // 位移
        canvas.translate(250,250);
        // 绘制一个圆
        canvas.drawCircle(0,0,100,paint);

        mPicture.endRecording();
    }


    public Canvas1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        recording();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPicture(mPicture);
    }

}
