package com.example.a18.path.spider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class SpiderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1<<10, 1<<10);
        setContentView(R.layout.activity_main);

        SpiderWebView viewById = (SpiderWebView) findViewById(R.id.swb);
        viewById.setProgress(0.2f, 0.4f, 0.6f, 0.8f, 1.0f, 0.2f);

    }
}
