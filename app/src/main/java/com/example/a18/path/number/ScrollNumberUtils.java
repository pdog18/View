package com.example.a18.path.number;


import android.animation.TypeEvaluator;

import timber.log.Timber;

public class ScrollNumberUtils implements TypeEvaluator<NumberValue>{

    double startValueValue;
    double endValueValue;
    NumberValue number  = new NumberValue(0);


    @Override
    public NumberValue evaluate(float fraction, NumberValue startValue, NumberValue endValue) {
        startValueValue = startValue.getValue();
        endValueValue = endValue.getValue();

        Timber.d("startValue = %s", startValue);
        Timber.d("endValue = %s", endValue);

        Timber.d("fraction = %s", fraction);
        double dValue = endValueValue - startValueValue;
        number.setValue(dValue * fraction);
        return number;
    }
}
