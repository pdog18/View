package com.example.a18.path.scrollchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by pdog on 2017/12/8.
 */

public class RV extends RecyclerView {

    private Paint paint;

    public RV(Context context) {
        this(context, null);
    }

    public RV(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(244,244,244));
        paint.setStrokeWidth(3);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawLine(0, getHeight() / 20.0f, getWidth(), getHeight() / 20.0f, paint);
        c.drawLine(0, getHeight() / 3.0f, getWidth(), getHeight() / 3.0f, paint);
        c.drawLine(0, getHeight() / 3.0f * 2, getWidth(), getHeight() / 3.0f * 2, paint);
    }

    float x ;   //记录下x坐标 ，用作信息显示

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            x = ev.getX();
        }
        return super.dispatchTouchEvent(ev);
    }

    public float getMotionEventUp(){
        return x;
    }
}
