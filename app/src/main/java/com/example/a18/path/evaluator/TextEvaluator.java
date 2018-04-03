package com.example.a18.path.evaluator;

import android.animation.TypeEvaluator;

public class TextEvaluator implements TypeEvaluator<MoneyText> {
    private MoneyText moneyText = new MoneyText(0);


    public TextEvaluator(int index) {
        moneyText.setIndex(index);
    }


    @Override
    public MoneyText evaluate(float fraction, MoneyText startValue, MoneyText endValue) {
        return moneyText.update(endValue.total, fraction);
    }
}
