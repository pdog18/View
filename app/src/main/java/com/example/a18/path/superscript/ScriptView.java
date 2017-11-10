package com.example.a18.path.superscript;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/10  16 :41
 */

public class ScriptView extends TextView {

    private Picture mPicture;

    public ScriptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Path path = new Path();
        path.moveTo(w - h / 2, 0);
        path.lineTo(w, h / 2);
        path.lineTo(w, h / 2 - 60);
        path.lineTo(w - h / 2 + 60, 0);
        path.close();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextSize(40);

        mPicture = new Picture();
        Canvas canvas = mPicture.beginRecording(0, 0);
        canvas.drawPath(path, paint);
        canvas.drawTextOnPath("测试条幅",path,0,0,paint2);
        mPicture.endRecording();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPicture(mPicture);
    }
}
