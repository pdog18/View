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
//            Timber.d("dx = %s", dx);
//            Timber.d("left = %s", left);
//            final int leftBound = getPaddingLeft();
//            final int rightBound = getWidth() - child.getWidth();
//            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
//
//            Timber.d("newLeft = %s", newLeft);
            Timber.d("left = %s", left);
            Timber.d("dx = %s", dx);
            return left;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return child == slide ? child.getWidth() : 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Timber.d("releasedChild.getLeft() = %s", releasedChild.getLeft());

            if (releasedChild.getLeft() < releasedChild.getWidth()) {
                Toast.makeText(releasedChild.getContext(), "<", Toast.LENGTH_SHORT).show();
                viewDragHelper.settleCapturedViewAt(autoBackViewOriginLeft, autoBackViewOriginTop);
                invalidate();
            } else {
                Toast.makeText(releasedChild.getContext(), "> or =", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            viewDragHelper.captureChildView(slide, pointerId);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    };
    private View slide;
    private int autoBackViewOriginLeft;
    private int autoBackViewOriginTop;

    public SlideBackLayout(Context context) {
        this(context, null);
    }

    public SlideBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        viewDragHelper = ViewDragHelper.create(this, 0.5f, callback);

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        AppCompatImageView imageView = new AppCompatImageView(getContext());
        LayoutParams params = new LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
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

            autoBackViewOriginLeft = slide.getLeft();
            autoBackViewOriginTop = slide.getTop();

            Timber.d("autoBackViewOriginLeft = %s", autoBackViewOriginLeft);
            Timber.d("autoBackViewOriginTop = %s", autoBackViewOriginTop);

        } else {
            Timber.e("slide == null");
        }

    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
