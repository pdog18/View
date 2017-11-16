package com.example.a18.path.number;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by machao on 2017/11/16.
 */

public class NumberAnim extends View {

    private static final String TAG = "NumberAnim";

    private final Paint paint;
    private Paint linePaint;

    private ObjectAnimator objectAnimator;

    public NumberAnim(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(56);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(2);
        linePaint.setColor(Color.BLACK);

        setLayerType(LAYER_TYPE_SOFTWARE, null);


        float height = paint.getFontSpacing();
        allHeight = (int) (height * 11);
    }

    int allHeight;  //11个字符的高度


    String[] numbers ;
    {
        numbers = new String[30];
        for (int i = 0; i < 30; i++) {
            numbers[i] = String.valueOf(i % 10);
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float width = paint.measureText(numbers[0]);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int) width,MeasureSpec.EXACTLY),heightMeasureSpec);
    }


    //启动滚动数字动画
    public void start() {
        System.out.println("start");
        float fontSpacing = paint.getFontSpacing();
        System.out.println(fontSpacing + " font Spacing");
        objectAnimator = ObjectAnimator.ofFloat(this, "progress", 1f);
        objectAnimator.setDuration(5000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animing = false;
                invalidate();
            }
        });
        objectAnimator.start();
    }

    int lastY = 0;
    int startY = 0;

    int index = 5;


    private void setProgress(float progress) {
        if (startY == 0){
            startY = ((int) ((index ) * paint.getFontSpacing()) );
            scrollTo(0,startY);

        }
        int currY = (int) (allHeight /11f*10f * (progress) * 2);

        int dy = currY - lastY;

        lastY = currY;

        if (getScrollY() - startY  >= paint.getFontSpacing() *10){
            scrollTo(0,startY);
        }else {
            scrollBy(0, dy);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    boolean animing = false;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        super.onDraw(canvas);

        if (!animing) {//不在动画时，只会显示默认的数字
            scrollTo(0,0);
            canvas.drawText(numbers[index],0,paint.getFontSpacing() - paint.descent(),paint);

        } else { //处于动画的话，会绘制所有的数字

            int x = 0;
            int y = (int) paint.getFontSpacing();

            for (int i = 0; i < numbers.length; i++) {
                int cy = y * (i + 1);
                canvas.drawText(numbers[i], x, cy - paint.descent(), paint);
//                canvas.drawLine(0, cy, 500, cy, linePaint);
            }

        }
    }

}
