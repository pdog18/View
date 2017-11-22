package com.example.a18.path.znfxchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class ZnfxActivity extends AppCompatActivity {
    private static final String TAG = "ZnfxActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znfx);

        ZNFXChart znfx = ((ZNFXChart) findViewById(R.id.znfx));

        float[] floats = new float[30];

        float[] floats1 = new float[30];
        float[] floats2 = new float[30];

        for (int i = 0; i < 30; i++) {
            double random = Math.random();
            floats[i] = (float) (random * 60000 + 26000);
        }
        for (int i = 0; i < 30; i++) {
            double random = Math.random();
            floats1[i] = (float) (random * 20000 + 66000);
        }

        for (int i = 0; i < 30; i++) {
            double random = Math.random();
            floats2[i] = (float) (random * 40000 + 46000);
        }


        znfx.setPeakAndValley(99487, 15971)
                .addKLine(new KLine.Builder(floats)
                        .setColor(Color.BLUE)
                        .setWidth(20).build())
                .addKLine(new KLine.Builder(floats1)
                        .setWidth(20)
                        .setColor(Color.GREEN)
                        .build())
                .addKLine(new KLine.Builder(floats2)
                        .setWidth(10)
                        .setColor(Color.RED)
                        .build());

        znfx.startAnim();

    }
}
