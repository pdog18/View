package com.example.a18.path.slidebacklayout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a18.path.R;

import timber.log.Timber;


public class SlideBackLayout extends FrameLayout {
    ViewDragHelper viewDragHelper;
    ViewDragHelper.Callback callback = new Callback() {
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Timber.d("left = %s", left);
            Timber.d("dx = %s", dx);

            int absLeft = Math.abs(left);
            int width = child.getWidth();

            float ratio = absLeft * 1.0f / width;
            float alphaRatio = 1.0f - ratio;
            if (alphaRatio < 0.3f) {
                alphaRatio = 0.3f;
            }

            child.setAlpha(alphaRatio);
            return Math.min(left, 0);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return child == SlideBackLayout.this.leftChild ? child.getWidth() : 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Timber.d("xvel = %s", xvel);
            if (releasedChild.getRight() >= releasedChild.getWidth() * 0.8f) {
                Toast.makeText(releasedChild.getContext(), "callback", Toast.LENGTH_SHORT).show();
                releasedChild.animate()
                    .alpha(0)
                    .setDuration(500)
                    .withEndAction(() -> {
                        layoutLeftChild(releasedChild);
                        releasedChild.setAlpha(1);
                    })
                    .start();
            }else {
                Toast.makeText(releasedChild.getContext(), "return", Toast.LENGTH_SHORT).show();
                viewDragHelper.settleCapturedViewAt(-releasedChild.getWidth(), releasedChild.getTop());
                invalidate();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            viewDragHelper.captureChildView(leftChild, pointerId);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    };
    private int autoBackViewOriginLeft;
    private int autoBackViewOriginTop;
    private AppCompatImageView leftChild;

    public SlideBackLayout(Context context) {
        this(context, null);
    }

    public SlideBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        viewDragHelper = ViewDragHelper.create(this, callback);
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftChild = new AppCompatImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leftChild.setLayoutParams(params);
        leftChild.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        leftChild.setImageResource(R.mipmap.slideback);
        addView(leftChild);
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




        if (leftChild != null) {
            layoutLeftChild(leftChild);
        } else {
            Timber.e("slide == null");
        }

    }

    private void layoutLeftChild(View leftChild) {
        int width = leftChild.getMeasuredWidth();
        int height = leftChild.getMeasuredHeight();


        int slideLeft;
        int slideRight;
        int slideTop;
        int slideBottom;

        slideRight = 0;
        slideLeft = slideRight - width;

        int parentHeight = getMeasuredHeight();
        slideTop = parentHeight / 2 - height / 2;
        slideBottom = parentHeight / 2 + height / 2;

        leftChild.layout(slideLeft, slideTop, slideRight, slideBottom);
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
