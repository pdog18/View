package com.example.a18.path.scale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.a18.path.R;

/**
 * desc: todo 描述本类功能
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/12  15 :09
 */

public class ScaleFrame extends FrameLayout {

    private final Bitmap mBitmap;

    public ScaleFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);

    }

    float x = 1f;
    float y = 1f;

    public void setScale(float x, float y) {
        this.x = x;
        this.y = y;
        invalidate();
    }

    Rect mRect = new Rect();

Paint mPaint = new Paint();

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//
//        canvas.save();
//        canvas.translate(getWidth() / 2, getHeight() / 2);
//        canvas.scale(x *3, y *3);
//        canvas.drawBitmap(mBitmap,-mBitmap.getWidth()/2,-mBitmap.getHeight()/2,mPaint);
//        canvas.restore();
//        System.out.println("onDraw");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(2,2);
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(x *3, y *3);
        canvas.drawBitmap(mBitmap,-mBitmap.getWidth()/2,-mBitmap.getHeight()/2,mPaint);
        canvas.restore();
        System.out.println("onDraw");

    }
}
