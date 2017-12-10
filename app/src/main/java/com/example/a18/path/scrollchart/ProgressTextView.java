package com.example.a18.path.scrollchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 柱状图下方的带三角形指示器的文字
 */

@SuppressWarnings("all")
public class ProgressTextView extends TextView {

    private Path path;
    private Paint paint;

    public ProgressTextView(Context context) {
        this(context, null);
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#5092ff"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = new Path();
        //画三角形
        path.moveTo(getWidth()/2.0f,getHeight()/5.0f * 4);

        path.rLineTo(getWidth()/5.0f,getWidth()/5.0f);
        path.rLineTo(-getWidth()/ 5.0f * 2,0);
        path.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }
}
