package com.example.a18.path.number;

import android.text.SpannableStringBuilder;

public class NumberValue extends SpannableStringBuilder {

    private double value;

    public NumberValue(double value) {
        super(MoneyFormatUtil.getFormatMoney(value,2));
        setValue(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
