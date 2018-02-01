package com.example.a18.path.arcseekbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.a18.path.R;

import timber.log.Timber;


public class ArcView extends View {

    private Paint paint;
    private RectF rectF;
    private float rotateProgress;
    private int halfWidth;
    private Bitmap drawable;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        rotateProgress = 0;

        drawable = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 20, 20, 20}, 0));
    }


    double lastAngle;
    double startAngle;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startAngle = getAngle(event);
                return true;
            case MotionEvent.ACTION_MOVE:

                double angle = getAngle(event);
                double v = angle - startAngle;

                Timber.d("本次相对与down的位置移动的角度 ，v = %s", v);
                rotateProgress = (float) (lastAngle - v);
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastAngle = rotateProgress;
                return true;
        }
        return super.onTouchEvent(event);
    }


    @SuppressWarnings("all")
    private double getAngle(MotionEvent event) {
        float x = event.getX() - getWidth() / 2;
        float h = getWidth() * 1.0f * 3 / 2 - getHeight();
        float y = event.getY() + h;

        double tan = Math.atan2(x, y);
        double angleA = 180 * tan / Math.PI;
        return angleA;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int padding = getHeight() / 4;
        halfWidth = (int) (getWidth() * 1.5f) - padding;
        rectF = new RectF(-halfWidth, -halfWidth, halfWidth, halfWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() - getWidth() * 1.5f);
        canvas.rotate(rotateProgress);

        canvas.drawArc(rectF, 90, -90, false, paint);

        drawBitmap(canvas);

        canvas.restore();
    }

    private void drawBitmap(Canvas canvas) {
        int degrees = 90;
        canvas.save();
        canvas.rotate(degrees);
        canvas.drawBitmap(drawable, halfWidth, 0, null);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees - 30);
        canvas.drawBitmap(drawable, halfWidth, 0, null);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees - 60);
        canvas.drawBitmap(drawable, halfWidth, 0, null);
        canvas.restore();
    }
}
