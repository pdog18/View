package pdog18.com.module_flipper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * author: pdog
 * email: pdog@qq.com
 * time: 2017/11/10  13 :37
 */

public class Flipper extends ViewFlipper {

    private Animation mIn;
    private Animation mOut;
    private int mAnimationTime = 300;
    private int flipIntervalTime = 1200;


    public Flipper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIn = new TranslateAnimation(0, 0, 100, 0);
        mOut = new TranslateAnimation(0, 0, 0, -100);

        updateAnimation();
    }

    private void updateAnimation() {
        mIn.setDuration(mAnimationTime);
        mOut.setDuration(mAnimationTime);
        setInAnimation(mIn);
        setOutAnimation(mOut);
    }

    public Flipper setDuration(int animationTime) {
        mAnimationTime = animationTime;
        updateAnimation();
        return this;
    }

    public Flipper setIntervalTime(int intervalTime) {
        flipIntervalTime = intervalTime;
        setFlipInterval(flipIntervalTime);
        return this;
    }

    public Flipper setTexts(List<String> strings) {
        stopFlipping();

        removeAllViews();
        ViewGroup.LayoutParams params = new ViewPager.LayoutParams();
        params.height = 100;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        for (String string : strings) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(params);
            textView.setText(string);
            textView.setTextColor(Color.RED);
            addView(textView);
        }

        startFlipping();

        return this;
    }


}
