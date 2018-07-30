package pdog18.com.module_matrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * desc: todo 描述本类功能
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/12  15 :45
 */

public class MatrixMapView extends View {
    public MatrixMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(30);
    }

    float x;
    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        xy[0] = event.getRawX();
        xy[1] = event.getRawY();

        invalidate();

        System.out.println("onTouchEvent        xy[0] = " + xy[0] + "xy[1] = " + xy[1]);
        return true;
    }

    Paint mPaint = new Paint();
    Matrix mMatrix = new Matrix();

    float[] xy = new float[2];

    /**
     * 因为 使用了 translate ，坐标系发生了改变。
     * 现在在onTouchEvent中获取的时候通过 getRawX 等直接获取屏幕的坐标系。
     * 然后通过获取当前canvas矩阵的逆矩阵然后将屏幕的坐标系转换为当前的坐标系
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.getMatrix().invert(mMatrix);
        mMatrix.mapPoints(xy);
        System.out.println("onDraw        xy[0] = " + xy[0] + "xy[1] = " + xy[1]);
        canvas.drawPoint(xy[0], xy[1], mPaint);
        canvas.restore();
    }
}
