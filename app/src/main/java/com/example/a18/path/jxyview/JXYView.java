package com.example.a18.path.jxyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by machao on 2017/11/22.
 */

public class JXYView extends android.support.v7.widget.AppCompatImageView {

    private final Paint paint;
    private int textHeight;//文字的高度

    String text = "";

    public JXYView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(120);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.RIGHT);
    }


    /**
     * 获得根据文字的高度调整
     */
    private int measueTextHeight(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    /**
     * 设置右上角的文字
     */
    public void setText(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        textHeight = measueTextHeight(text);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth(), textHeight);
        canvas.drawText(text, 0, text.length(), 0, 0, paint);
        canvas.restore();
    }
}
