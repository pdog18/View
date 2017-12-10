package com.example.a18.path.scrollchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 *  表格带有 黑色基准线，重写了onDraw(Canvas canvas) 来绘制了两条基准线
 */

public class RecyeclerViewWithBaseLine extends RecyclerView {

    private Paint paint;

    public RecyeclerViewWithBaseLine(Context context) {
        this(context, null);
    }

    public RecyeclerViewWithBaseLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyeclerViewWithBaseLine(Context context, AttributeSet attrs, int defStyle) {
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

}
