package com.example.a18.path.wave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class WaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        final WaveView viewById = findViewById(R.id.bezier);
        viewById.post(new Runnable() {
            @Override
            public void run() {
                viewById.start();
            }
        });

    }
}
