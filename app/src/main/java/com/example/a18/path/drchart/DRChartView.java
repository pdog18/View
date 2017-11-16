package com.example.a18.path.drchart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by machao on 2017/11/15.
 */

public class DRChartView extends View {

    float progress ; //动画的进度

    private Paint paint;//k线的画笔
    private Path path;//k线的path

    private Path underK;//k线下方的填充区域
    private Paint underKPaint ; //k线下方的填充区域画笔
    private ObjectAnimator ofInt = ObjectAnimator.ofFloat(this, "progress", 1f);;

    public DRChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);//关闭硬件加速
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        underKPaint = new Paint();
        underKPaint.setAntiAlias(true);
        underKPaint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(0,900,0,1800,Color.CYAN,Color.WHITE,Shader.TileMode.CLAMP);
        underKPaint.setShader(shader);


        for (int i = 0; i < 21; i+= 2) {
            points[i] = 1080 /20 * i;
        }



        for (int i = 1; i < 22; i+=2) {
            points[i] = new Random().nextInt(200) + 900;
        }

    }

    int[] points = new int[40];

    private static final String TAG = "DRChartView";
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = new Path();
//        path.moveTo(0,);
        Log.e(TAG,w + "");
        Log.e(TAG, h + " h ");
        path.moveTo(points[0],points[1]);
        for (int i = 2; i < 21; i+=2) {
            path.lineTo(points[i],points[i+1]);
        }
//        path.lineTo(w,h);
        paint.setPathEffect(new CornerPathEffect(40));
        underKPaint.setPathEffect(new CornerPathEffect(40));



        rect = new Rect(0,0,w,h);
        underK = new Path();
        underK.moveTo(points[0],points[1]);
        underK.addPath(path);
        underK.lineTo(w,h);
        underK.lineTo(0,h);
        underK.close();
    }

    Rect rect;


    public void start(){
        System.out.println("start");
        ofInt.setDuration(500);
        ofInt.start();

    }

    public void setProgress(float progress){
        this.progress = progress;
        rect.right = ((int) (progress  * getWidth()));

        invalidate();
    }



    private float getProgress(){
        Log.e(TAG,progress + "");
        return progress;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(rect);
        canvas.drawPath(path,paint);
        canvas.drawPath(underK,underKPaint);

        canvas.restore();
    }
}
