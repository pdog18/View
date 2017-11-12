package com.example.a18.path.canvas1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * desc: todo 描述本类功能
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/12  11 :04
 */

public class Canvas2View extends View {
    public Canvas2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "translationX", 500);

        objectAnimator.start();

        ViewPropertyAnimator animate = animate();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
