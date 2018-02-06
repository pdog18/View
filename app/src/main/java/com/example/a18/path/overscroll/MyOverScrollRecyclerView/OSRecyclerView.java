package com.example.a18.path.overscroll.MyOverScrollRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mixiaoxiao.overscroll.OverScrollDelegate;
import com.mixiaoxiao.overscroll.OverScrollDelegate.OverScrollable;



public class OSRecyclerView extends RecyclerView implements OverScrollDelegate.OverScrollable {
    private OverScrollDelegate mOverScrollDelegate;

    public OSRecyclerView(Context context) {
        this(context, null);
    }

    public OSRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OSRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.createOverScrollDelegate(context);
    }

    private void createOverScrollDelegate(Context context) {
        this.mOverScrollDelegate = new OverScrollDelegate(this);
        this.setOverScrollMode(0);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.mOverScrollDelegate.onInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mOverScrollDelegate.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void draw(Canvas canvas) {
        this.mOverScrollDelegate.draw(canvas);
    }

    void absorbGlows(int velocityX, int velocityY) {
        this.mOverScrollDelegate.recyclerViewAbsorbGlows(velocityX, velocityY);
    }

    public int superComputeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int superComputeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    public int superComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void superOnTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
    }

    public void superDraw(Canvas canvas) {
        super.draw(canvas);
    }

    public boolean superAwakenScrollBars() {
        return super.awakenScrollBars();
    }

    public boolean superOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    public View getOverScrollableView() {
        return this;
    }

    public OverScrollDelegate getOverScrollDelegate() {
        return this.mOverScrollDelegate;
    }
}
