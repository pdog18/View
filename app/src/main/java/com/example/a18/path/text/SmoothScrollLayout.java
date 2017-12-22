package com.example.a18.path.text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import timber.log.Timber;

/**
 * 评级处缓慢上下滑动的控件
 */
public class SmoothScrollLayout extends LinearLayout {
    Scroller scroller;
    public SmoothScrollLayout(Context context) {
        this(context, null);
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        scroller = new Scroller(context);
    }

    int[] childHeight = new int[128];

    int totalHeight = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            totalHeight += child.getMeasuredHeight();
            childHeight[i] = totalHeight;
            Timber.d("totalHeight = %s", totalHeight);
        }

    }


    public void smoothTo(int position) {
        Timber.d("position = %s", position);
        scroller.startScroll(0,getScrollY(),0,childHeight[position] -getScrollY(),3000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        Timber.d("computeScroll: %s%s" ,scroller.getCurrX(),scroller.getCurrY());

        if (scroller.computeScrollOffset()) {
            scrollTo(0,scroller.getCurrY());
            invalidate();
        }
    }
}
