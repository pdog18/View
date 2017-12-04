package com.example.a18.path.motionevent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import static android.content.ContentValues.TAG;

/**
 * Created by pdog on 2017/12/3.
 */

public class MotionEventViewGrounp extends FrameLayout {

    boolean consume = false;
    private Paint paint;

    public MotionEventViewGrounp(Context context) {
        this(context, null);
    }

    public MotionEventViewGrounp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MotionEventViewGrounp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(48);
    }

    float x;
    float y;
    float rawX;
    float rawY;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() == 0 ){
            return;
        }

        System.out.println(((View) getParent()).getMeasuredHeight());
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, "onMeasure:      UNSPECIFIED" );
                Log.d(TAG, "onMeasure:  size =  " + size);

                break;
            case MeasureSpec.AT_MOST:
                Log.d(TAG, "onMeasure:      AT_MOST " );
                Log.d(TAG, "onMeasure:  size =  " + size);

                break;
            case MeasureSpec.EXACTLY:
                Log.d(TAG, "onMeasure:      EXACTLY" );
                Log.d(TAG, "onMeasure:  size =  " + size);

                    break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() != 0) {
            Log.d(TAG, "onLayout: ");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();

                rawX = event.getRawX();
                rawY = event.getRawY();
                invalidate();
return true;
                case MotionEvent.ACTION_UP:
                    requestLayout();
                    break;
        }


        return super.onTouchEvent(event);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (getChildCount() > 0) {
            canvas.save();
            canvas.translate(x, y);
            canvas.drawLine(-rawX, 0, 0, 0, paint);
            canvas.drawText("rawX = " + rawX, -x / 2, 0, paint);

            canvas.drawLine(0, -rawY, 0, 0, paint);
            canvas.drawText("rawY = " + rawY, 0, -y / 2, paint);

            canvas.restore();

            View child = getChildAt(0);
            int left = child.getLeft();
            int top = child.getTop();
            canvas.save();
            canvas.translate(left, top);
            canvas.drawLine(-left, 0, 0, 0, paint);
            canvas.drawText("left = " + left, -left / 2, 0, paint);
            canvas.drawLine(0, -top, 0, 0, paint);
            canvas.drawText("top = " + top, 0, -top / 2, paint);
            canvas.restore();

        } else {

            canvas.save();
            canvas.translate(x, y);
            canvas.save();
            canvas.translate(0, 100);
            canvas.drawLine(-x, 0, 0, 0, paint);
            canvas.drawText("X = " + x, -x / 2, 0, paint);
            canvas.restore();

            canvas.save();
            canvas.translate(100, 0);
            canvas.drawText("Y = " + y, 0, -y / 2, paint);
            canvas.drawLine(0, -y, 0, 0, paint);
            canvas.restore();
            canvas.restore();
        }

    }
}
