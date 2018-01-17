package com.example.a18.path.number;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class ScrollNumber extends View {

    int next;
    int curr;


    int mTextHeight;
    int offset;
    private Paint textPaint;

    int target = 9;
    int start = 0;


    int cycle = 3;
    private int mTextWidth;

    public ScrollNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    private static final String TAG = "ScrollNumber";

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(128);
        Rect rect = new Rect();
        textPaint.getTextBounds("0",0,1,rect);

        textPaint.setTextAlign(Paint.Align.CENTER);

        mTextHeight = rect.height();
        mTextWidth = rect.width();

        curr = 0;
        next = 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = resolveSize(mTextHeight , heightMeasureSpec);
        int width = resolveSize(mTextWidth , widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void startScroll() {
        scrolling();
    }


    private void scrolling() {
        /**
         * 1 ，计算出当前需要移动的offset
         * 2 ，计算出当前的绘制的数值 curr , next
         * 3 ，刷新布局
         */

        //1
        offset += 10;

        //2
        if (offset >= mTextHeight ){
            offset = 0;
            curr = next;
            next = (next + 1) %10;
        }

        //3
        invalidate();
    }

    private Runnable r = this::scrolling;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        /**
         * 1， 移动画布
         * 2， 绘制当前的数值
         * 3， 绘制下一个数值
         * 4， 判断是否需要继续移动
         */

        translate(canvas);
        drawCurr(canvas);
        drawNext(canvas);


        /**
         * 判断是否已经达到目标位置
         */
        if (curr != target) {
            postDelayed(r, 0);
        }else {
            removeCallbacks(r);
        }
    }


    /**
     * 移动画布
     * @param canvas
     */
    private void translate(Canvas canvas) {
        canvas.translate(0, -offset);

    }


    /**
     * 绘制当前
     *
     * @param canvas
     */
    private void drawCurr(Canvas canvas) {
        canvas.drawText(String.valueOf(curr), mTextWidth/2, mTextHeight , textPaint);
    }


    /**
     * 绘制下一个
     *
     * @param canvas
     */
    private void drawNext(Canvas canvas) {
        canvas.drawText(String.valueOf(next),  mTextWidth /2 , mTextHeight *2 , textPaint);
    }


}
