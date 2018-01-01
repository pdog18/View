package com.example.a18.path.text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import timber.log.Timber;

/**
 * 评级/策略/突破 处缓慢上下滑动的控件
 */
public class SmoothScrollLayout extends LinearLayout implements ChildViewChangedListener, com.example.a18.path.text.FirstViewChangeListener {
    private Scroller scroller;

    private BaseSmoothAdapter adapter;

    View lastChild;

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
        setFirstViewChangeListener(this);
        setViewChangedListener(this);
    }

    public void setAdapter(BaseSmoothAdapter adapter) {
        this.adapter = adapter;
        requestLayout();
    }

    boolean first = true;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (first) {
            first = false;
            makeAndAddView();
        }
    }

    private void makeAndAddView() {
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {

            View child = adapter.getView(this,i);

            if (i == 0) {
                lastChild = child;
                if (FirstViewChangeListener != null) {
                    FirstViewChangeListener.onFirstChange(lastChild);
                }
            }

            addView(child);
            child.requestLayout();
        }
    }

    /**
     * 因为 heightArray 中存放的childHeight是类似这种情况的 1 - 2 - 3,但是 child在排放的时候是3-2-1 这样的
     * 所以dy 取值的时候 需要充 heightArray中反向取出
     *
     * @param position
     */
    public void smoothTo(int position) {

        View currChild = getChildAt(position);
        Timber.d("currChild = %s", currChild);

        if (currChild == lastChild) {
            return;
        }
        if (lastChild == null || currChild == null) {
            return;
        }

        Timber.d("position = %s", position);

        if (ViewChangedListener != null) {
            ViewChangedListener.onViewChanged(lastChild, currChild);
        }

        Timber.d("currChild.getTop() = %s", currChild.getTop());

        scroller.abortAnimation();
        scroller.startScroll(0,getScrollY(),0,currChild.getTop() -getScrollY(),3000);

        lastChild = currChild;

        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }


    @Override
    public void onViewChanged(View last, View current) {
        last.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(1000)
            .start();

        current.animate()
            .scaleX(1.5f)
            .scaleY(1.5f)
            .setDuration(1000)
            .start();
    }

    private ChildViewChangedListener ViewChangedListener;

    public void setViewChangedListener(ChildViewChangedListener ViewChangedListener) {
        this.ViewChangedListener = ViewChangedListener;
    }

    private FirstViewChangeListener FirstViewChangeListener;

    public void setFirstViewChangeListener(FirstViewChangeListener FirstViewChangeListener) {
        this.FirstViewChangeListener = FirstViewChangeListener;
    }

    @Override
    public void onFirstChange(View first) {
        first.animate()
            .scaleX(1.5f)
            .scaleY(1.5f)
            .setDuration(1000)
            .start();
    }
}
