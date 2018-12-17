package pdog18.com.module_patheffect;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class PathEffectView extends View {

    private Path line;
    private Paint linePaint;


    private Path dst;
    private Paint dstPaint;

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new CornerPathEffect(300));

        line = new Path();

        line.moveTo(0, 1000);
        line.rLineTo(200, -150);
        line.rLineTo(200, 300);
        line.rLineTo(200, -350);
        line.rLineTo(200, 320);
        line.rLineTo(200, -370);
        line.lineTo(w, 800);


        dst = new Path();
        linePaint.getFillPath(line, dst);
        linePaint.setStrokeWidth(10);

        dst.lineTo(w, h);
        dst.lineTo(0, h);
        dst.close();


        Shader Shader = new LinearGradient(0, 0, 0, h, Color.BLUE, Color.WHITE, android.graphics.Shader.TileMode.CLAMP);
        dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        dstPaint.setColor(Color.GREEN);
        dstPaint.setShader(Shader);
        dstPaint.setStyle(Paint.Style.FILL);

        dstPaint.setStrokeWidth(5);


        rect = new Rect(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        clipThis(canvas);
        canvas.drawPath(line, linePaint);

        canvas.drawPath(dst, dstPaint);
        canvas.restore();
    }

    private void clipThis(Canvas canvas) {
        canvas.clipRect(rect);
    }

    Rect rect = new Rect();

    @SuppressWarnings("unused")
    private void setProgress(float progress) {
        rect.right = (int) (progress * getWidth());
        invalidate();
    }

    public void start() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", 1);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }
}
