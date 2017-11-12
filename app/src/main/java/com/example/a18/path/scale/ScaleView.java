package com.example.a18.path.scale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.a18.path.R;

/**
 * desc: todo 描述本类功能
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/12  14 :54
 */

public class ScaleView extends Button {
    Paint mPaint = new Paint();

    private final Bitmap mBitmap;

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    float x = 1f;
    float y = 1f;

    public void setScale(float x, float y) {
        this.x = x;
        this.y = y;
        invalidate();
    }

    Rect mRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(x, y);
        canvas.drawBitmap(mBitmap, -mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2, mPaint);
        canvas.restore();
        System.out.println("onDraw");

    }
}
