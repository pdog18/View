package com.example.a18.path.evaluator;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;

public class MoneyText {
    private int left;       //小数点前
    private int right;      //小数点以后
    double total;
    private final SpannableStringBuilder content;
    private int factor;
    private int index = 2;

    public MoneyText(double total) {
        content = new SpannableStringBuilder();
        update(total, 1);
    }

    public MoneyText update(double endTotal, float fraction) {

        this.total = endTotal * fraction;
        this.left = ((int) (total));
        this.right = (int) Math.round((total - left) * factor);

        return this;
    }

    private void addLastZero(SpannableStringBuilder content) {
        for (int i = 0; i < index; i++) {
            content.append("0");
        }
    }

    public CharSequence getContent(int leftSize, int rightSize) {
        content.clear();       //清空字符串

        if (right == factor) { //类似0.99995四舍五入后需要向前进一位
            left += 1;
            right = 0;
        }

        String leftText = Integer.toString(left);
        content.append(leftText)
            .append(".")
            .setSpan(new AbsoluteSizeSpan(leftSize, true), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int beforeLength = content.length();

        if (right == 0) {
            addLastZero(content);
        } else {
            String rightText = Integer.toString(right);
            content.append(rightText);
        }

        content.setSpan(new AbsoluteSizeSpan(rightSize, true), beforeLength, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return content;
    }

    /**
     * @param index 保留小数点的位数
     */
    public void setIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("factor 不能小于0，当前为" + index);
        }

        this.index = index;
        this.factor = (int) Math.pow(10, index);
    }
}
