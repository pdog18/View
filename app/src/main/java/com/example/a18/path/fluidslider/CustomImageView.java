package com.example.a18.path.fluidslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import timber.log.Timber;

/**
 * Created by pdog on 2018/1/7.
 */

public class CustomImageView extends AppCompatImageView {

    private Paint paint;
    private Rect rectF;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initPaint();
        rectF = new Rect();

    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Timber.d("canvas.getHeight() = %s", canvas.getHeight());
        Timber.d("canvas.getWidth() = %s", canvas.getWidth());
        canvas.drawPoint(-50,0,paint);
        getLocalVisibleRect(rectF);
        Timber.d("rectF.width() = %s", rectF.width());
        Timber.d("rectF.height() = %s", rectF.height());

    }
}
