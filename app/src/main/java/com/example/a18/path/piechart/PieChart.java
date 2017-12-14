package com.example.a18.path.piechart;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
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

    float holeRadiusRate = 0.75f;
    int radius;
    PointF center = new PointF();
    private int[] angles;
    private int[] colors;
    private int startAngle = 269;
    private int currAngle = startAngle;
    private int gap = 2;        //中间的留白

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();

        angles = new int[]{90, 270};
        colors = new int[]{
                Color.rgb(110, 206, 241),
                Color.rgb(80, 146, 255)};
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = 0.1f;
        rectF = new RectF(padding*w,padding*h,(1-padding)*w,(1-padding)*h);
        radius = ((int) (rectF.height() * (1-padding)* holeRadiusRate/ 2));
        center.x = w / 2;
        center.y = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < angles.length; i++) {
            paint.setColor(Color.WHITE);
            canvas.drawArc(rectF, currAngle, gap, true, paint);
            currAngle += gap;
            paint.setColor(colors[i]);
            canvas.drawArc(rectF, currAngle, angles[i] - gap, true, paint);
            currAngle += (angles[i] - gap);
        }

        paint.setColor(Color.WHITE);
        canvas.drawCircle(center.x, center.y, radius, paint);
    }

    private static final String TAG = "PieChart";
}
