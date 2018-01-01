package com.example.a18.path.slidebacklayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.a18.path.R;

import timber.log.Timber;


public class SlideBackLayout extends FrameLayout {
    ViewDragHelper viewDragHelper;
    ViewDragHelper.Callback callback = new Callback() {
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Timber.d("dx = %s", dx);
            Timber.d("left = %s", left);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - child.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            Timber.d("newLeft = %s", newLeft);
            return newLeft;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 100;
        }


        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);

        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            viewDragHelper.captureChildView(slide,pointerId);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    };
    private View slide;

    public SlideBackLayout(Context context) {
        this(context, null);
    }

    public SlideBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        viewDragHelper = ViewDragHelper.create(this, 0.5f,callback);

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        AppCompatImageView imageView = new AppCompatImageView(getContext());
        imageView.setBackgroundResource(R.mipmap.ic_launcher_round);
        imageView.setBackgroundColor(Color.RED);
        addView(imageView);

        Timber.d("getChildCount() = %s", getChildCount());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() > 2) {
            throw new IndexOutOfBoundsException("getChildCount() > 2");
        }


        View content = getChildAt(0);
        slide = getChildAt(1);

        if (slide != null) {
            int width = slide.getMeasuredWidth();
            int height = slide.getMeasuredHeight();


            int slideLeft;
            int slideRight;
            int slideTop;
            int slideBottom;

            slideRight = 0;
            slideLeft = slideRight - width;

            int parentHeight = getMeasuredHeight();
            slideTop = parentHeight / 2 - height / 2;
            slideBottom = parentHeight / 2 + height / 2;

            slide.layout(slideLeft, slideTop, slideRight, slideBottom);

        } else {
            Timber.e("slide == null");
        }

    }

}
