package com.example.a18.path.scratchview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 刮刮奖效果
 */
public class ScratchView extends AppCompatImageView {
    //被刮开的区域
    private  Path clipPath;

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        clipPath = new Path();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 记录移动的点；
                clipPath.addCircle(event.getX(),event.getY(),50, Path.Direction.CW);
                invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制可以刮开的区域
        canvas.drawColor(Color.GRAY);

        canvas.save();
        //裁剪出可以显示图片的区域
        canvas.clipPath(clipPath);
        //绘制原本的图片
        super.onDraw(canvas);
        canvas.restore();
    }
}
