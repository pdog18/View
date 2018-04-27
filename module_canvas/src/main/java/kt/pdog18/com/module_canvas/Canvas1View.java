package kt.pdog18.com.module_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * desc: todo 描述本类功能
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/10  11 :23
 */

public class Canvas1View extends View {

    private Paint mPaint;

    private Picture mPicture = new Picture();
    private float mDisX;
    private float mDisY;
    private double mRatio;
    private Rect mRect;


    public Canvas1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);


        mRect = new Rect(200, 200, 500, 500);
    }



    float firstX = -1;
    float firstY = -1;
    float secondX = -1;
    float secondY = -1;

    double distanceOnDown;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    //记录两个点的初始坐标
                    distanceOnDown = distance(event);
                    getCenter(event);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    STATUS = 1;
                    double distanceOnMove = distance(event);
                    mRatio = distanceOnMove / distanceOnDown;

                    System.out.println("mRatio  >> " + mRatio);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    // 手指离开屏幕时将临时值还原
                }
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开屏幕时将临时值还原
                break;
            default:
                break;
        }
        return true;
    }

    int STATUS;

    private double distance(MotionEvent event) {

        firstX = event.getX(0);
        firstY = event.getY(0);
        secondX = event.getX(1);
        secondY = event.getY(1);

        mDisX = firstX - secondX;
        mDisY = firstY - secondY;

        double distance = Math.sqrt(mDisX * mDisX + mDisY * mDisY);
        return distance;
    }

    int mx;
    int my;

    private float[] getCenter(MotionEvent event) {

        float x;
        float y;

        x = firstX - mDisX / 2;
        y = firstY - mDisY / 2;

        mx = (int) x;
        my = (int) y;
        invalidate();

        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(((int) mRatio),(int)mRatio);
//        canvas.scale(2,2);

        canvas.drawPoint(mx, my, mPaint);

        canvas.drawRect(mRect,mPaint);

        canvas.restore();

    }

}
