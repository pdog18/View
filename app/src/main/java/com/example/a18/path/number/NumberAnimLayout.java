package com.example.a18.path.number;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by machao on 2017/11/16.
 */

public class NumberAnimLayout extends LinearLayout {

    private CharSequence charSequence;

    public NumberAnimLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //创建一个容器，通过对容器中对textview做动画来模拟数字滚动
    }


    public void setText(CharSequence charSequence){
        this.charSequence = charSequence;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }
}
