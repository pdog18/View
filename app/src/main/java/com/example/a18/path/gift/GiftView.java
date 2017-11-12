package com.example.a18.path.gift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.a18.path.R;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/8  16 :43
 */

public class GiftView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Bitmap mDrawable;

    public GiftView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mPath.moveTo(getWidth() / 2, getHeight());

        int xh = getHeight() / 4;
        int xw = getWidth() / 6;
        mPath.cubicTo(xw * 5, xh * 3, xw, xh, getWidth() / 2, 0);

        mPathMeasure = new PathMeasure(mPath, false);

        createPen();
    }

    @SuppressLint("NewApi")
    private void createPen() {
        Path pentacle = new Path();
        //1. 算出5个顶点

        //2. 连接5个顶点


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 10;
        mDrawable = BitmapFactory.decodeResource(getResources(), R.drawable.star, opts);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.out.println(mDrawable);

        canvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mDrawable, 100, 100, mPaint);

//        Drawable drawable = new ShapeDrawable(new PathShape());


    }
}
