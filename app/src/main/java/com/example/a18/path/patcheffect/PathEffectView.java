package com.example.a18.path.patcheffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class PathEffectView extends View {

    private  Path line;
    private  Paint linePaint;


    private  Path range;
    private  Paint rangePaint;
    private final Paint paint3;

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new CornerPathEffect(300));

        rangePaint = new Paint(linePaint);

        rangePaint.setColor(Color.BLUE);
        rangePaint.setStyle(Paint.Style.FILL);


        paint3 = new Paint(linePaint);
        paint3.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        line = new Path();
        range = new Path();

        line.moveTo(0,1000);
        line.rLineTo(200,-150);
        line.rLineTo(200,300);
        line.rLineTo(200,-350);
        line.rLineTo(200,320);
        line.rLineTo(200,-370);
        line.lineTo(w,800);


        range.addPath(line);
        range.lineTo(w,h);
        rangePaint.setPathEffect(null);
        range.lineTo(0,h);
        range.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0,-20);
        canvas.drawPath(line, linePaint);
        canvas.restore();


        canvas.drawPath(range, rangePaint);
        canvas.drawPath(range,paint3);
    }
}
