package pdog18.com.module_materialdesign.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;


public class NumberTextView extends View {
    TextPaint leftPaint;
    TextPaint rightPaint;
    private float height;
    private float width;

    public NumberTextView(Context context) {
        this(context, null);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Timber.d("onMeasure");
        int width;
        if (leftText != null) {

            float v = leftPaint.measureText(leftText);
            float v1 = rightPaint.measureText(rightText);
            width = resolveSize((int) (v +v1), widthMeasureSpec);
             Timber.d("width = %s", width);
        } else {
             width = resolveSize(widthMeasureSpec, widthMeasureSpec);

        }
        int height = resolveSize(heightMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width,widthMeasureSpec),heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Timber.d("onLayout");
    }

    float number;
    String leftText;
    String rightText;

    public NumberTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        leftPaint = new TextPaint();
        leftPaint.setColor(Color.BLACK);
        rightPaint = new TextPaint(leftPaint);
        leftPaint.setTextSize(64);
        rightPaint.setTextSize(32);

        Paint.FontMetrics fontMetrics = leftPaint.getFontMetrics();
        height = fontMetrics.top - fontMetrics.bottom;
        width = leftPaint.measureText("8");

    }

    public void setNumber(float number) {

        Timber.d("setNumber");
        this.number = number;
        String[] text = String.valueOf(number).split("\\.");
        leftText = text[0] + ".";
        rightText = text[1];
        Timber.d("leftText = %s", leftText);
        Timber.d("rightText = %s", rightText);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (leftText == null) {
            return;
        }

        canvas.save();
        canvas.drawText(leftText, 0, -height, leftPaint);
        canvas.translate(leftText.length() * width, 0);
        canvas.drawText(rightText, 0, -height, rightPaint);
        canvas.restore();
    }
}
