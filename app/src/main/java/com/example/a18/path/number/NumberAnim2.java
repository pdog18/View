package com.example.a18.path.number;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by machao on 2017/11/17.
 */

public class NumberAnim2 extends View {
    private static final String TAG = "NumberAnim2";
    private int number;
    private int[] numbers;

    public NumberAnim2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setNumber(5);
    }

    public void setNumber(@IntRange(from = 0, to = 10) int number) {
        this.number = number;
        numbers = new int[11];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (number + i) % 10;
        }
    }
}
