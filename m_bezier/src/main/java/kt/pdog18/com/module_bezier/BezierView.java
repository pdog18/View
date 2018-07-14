package kt.pdog18.com.module_bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/8  16 :57
 */

public class BezierView extends View {


    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(dp(2));
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dp(20));


        mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.FILL);


        mBezierPath = new Path();

        mRegion1 = new Region();
        mRegion2 = new Region();
    }

    Region mRegion;

    Paint mPaint;
    Paint mRedPaint;
    Paint mTextPaint;

    Point mStartPoint;
    Point mConPoint1;
    Point mConPoint2;
    Point mEndPoint;


    Region mRegion1;
    Region mRegion2;

    Path mBezierPath;

    Path mControlCircle1;
    Path mControlCircle2;

    int mPressFlag;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mStartPoint != null) {
            return;
        }
        mStartPoint = new Point(0, getHeight() / 2);
        mConPoint1 = new Point(getWidth() / 4, getHeight() / 2);
        mConPoint2 = new Point(getWidth() / 4 * 3, getHeight() / 2);
        mEndPoint = new Point(getWidth(), getHeight() / 2);


        mControlCircle1 = new Path();
        mControlCircle1.moveTo(mConPoint1.x, mConPoint1.y);
        mControlCircle1.addCircle(mConPoint1.x, mConPoint1.y, dp(14), Path.Direction.CW);

        mRegion = new Region(0, 0, w, h);
        mRegion1 = new Region();
        mRegion1.setPath(mControlCircle1, mRegion);

        mControlCircle2 = new Path();
        mControlCircle2.addCircle(mConPoint2.x, mConPoint2.y, dp(14), Path.Direction.CW);
        mRegion2 = new Region();
        mRegion2.setPath(mControlCircle2, mRegion);

        updatePath();
    }


    private void updatePath() {
        mBezierPath.reset();
        mBezierPath.moveTo(mStartPoint.x, mStartPoint.y);
        mBezierPath.cubicTo(mConPoint1.x, mConPoint1.y, mConPoint2.x, mConPoint2.y, mEndPoint.x, mEndPoint.y);
    }

    private void refresh() {
        updatePath();
        invalidate();
    }


    public void setControl(int x, int y) {
        Point point;
        Path path;
        Region region;
        if (mPressFlag == 1) {
            point = mConPoint1;
            path = mControlCircle1;
            region = mRegion1;
        } else {
            point = mConPoint2;
            path = mControlCircle2;
            region = mRegion2;
        }

        point.x = x;
        point.y = y;
        path.reset();
        path.addCircle(x, y, dp(14), Path.Direction.CW);
        region.setPath(path, mRegion);

        refresh();
    }


    int dx = -1;
    int dy = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mRegion1.contains(x, y)) {
                    mPressFlag = 1;
                    dx = x - mConPoint1.x;
                    dy = y - mConPoint1.y;
                    return true;
                } else if (mRegion2.contains(x, y)) {
                    mPressFlag = 2;
                    dx = mConPoint2.x - x;
                    dy = mConPoint2.y - y;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (mPressFlag) {
                    case 1:
                        setControl(x - dx, y - dy);     //防止控制点直接跳到触点
                        break;
                    case 2:
                        setControl(x, y);
                        break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mBezierPath, mPaint);


        canvas.drawPath(mControlCircle1, mRedPaint);
        canvas.drawPath(mControlCircle2, mRedPaint);

        canvas.drawText("1", mConPoint1.x, mConPoint1.y, mTextPaint);
        canvas.drawText("2", mConPoint2.x, mConPoint2.y, mTextPaint);

        canvas.drawCircle(mStartPoint.x, mStartPoint.y, dp(14), mRedPaint);
        canvas.drawCircle(mEndPoint.x, mEndPoint.y, dp(14), mRedPaint);
    }


    private float dp(float px) {
        return px * getResources().getDisplayMetrics().density;
    }

}
