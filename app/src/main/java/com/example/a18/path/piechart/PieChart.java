package com.example.a18.path.piechart;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 费用支出的饼图
 */

public class PieChart extends View {

    private Paint paint;
    private RectF rectF;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0.1f * w, 0.1f * h, w * 0.9f, h * 0.9f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setColor(Color.rgb(110, 206, 241));
        canvas.drawArc(rectF, 270, 90, true, paint);

        paint.setColor(Color.rgb(80, 146, 255));
        canvas.drawArc(rectF, 0, 270, true, paint);



        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF,269,2,true,paint);

        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF,-1,2,true,paint);



        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/3,paint);
    }

    private static final String TAG = "PieChart";
}
