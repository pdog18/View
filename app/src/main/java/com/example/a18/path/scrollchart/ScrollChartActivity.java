package com.example.a18.path.scrollchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a18.path.R;

public class ScrollChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_chart);
        Log.d("Tag", "onCreate: ");
        findViewById(R.id.scrollchart).post(new Runnable() {
            @Override
            public void run() {
                Log.d("Tag", "run: Post ");
                ScrollChart viewById = (ScrollChart) findViewById(R.id.scrollchart);
                viewById.startAnim();
            }
        });
    }
}
