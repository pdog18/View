package com.example.a18.path.znfxchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
            Log.e(TAG, "onCreate: " + floats[i] );
        }
        for (int i = 0; i < 30; i++) {
            double random = Math.random();
            floats1[i] = (float) (random * 20000 + 66000);
            Log.e(TAG, "onCreate: " + floats1[i] );
        }

        for (int i = 0; i < 30; i++) {
            double random = Math.random();
            floats2[i] = (float) (random * 40000 + 46000);
            Log.e(TAG, "onCreate: " + floats2[i] );
        }


        znfx.setPeakAndValley(99487,15971)
                .addPath(0, floats)
                .addPath(1,floats1)
                .addPath(2,floats2)
                .setPaintColor(1,Color.RED)
                .setPaintWidth(1,10)
                .setPaintColor(2,Color.BLACK)
                .setPaintWidth(2,10)
                .setPaintWidth(0, 10)
                .setPaintColor(0, Color.GREEN);

        znfx.startAnim();

    }
}
