package com.example.a18.path.constraintlayout;

import android.view.animation.Interpolator;

import static java.lang.Math.PI;


public class SpringInterpolator implements Interpolator {

    private final float factor;

    public SpringInterpolator(float factor) {
        super();
        this.factor = factor;
    }
    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * PI) / factor) + 1);
    }
}
