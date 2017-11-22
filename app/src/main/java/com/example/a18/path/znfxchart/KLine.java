package com.example.a18.path.znfxchart;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by machao on 2017/11/22.
 * 图表每条线封装的对象
 */

public class KLine {
    Path path;
    Paint paint;
    float[] points;

    private KLine(float[] points, Paint paint) {
        path = new Path();
        this.points = points;
        this.paint = paint;
    }


    public static class Builder {
        Paint paint;
        float[] points;

        public Builder(float[] points) {
            this.points = points;
            this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
        }

        public Builder setWidth(float width) {
            this.paint.setStrokeWidth(width);
            return this;
        }


        public Builder setColor(int color) {
            this.paint.setColor(color);
            return this;
        }

        public KLine build() {
            return new KLine(this.points,this.paint);
        }

    }
}
