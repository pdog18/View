package com.example.a18.path.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a18.path.R;

public class BezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        findViewById(R.id.bezier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BezierView) view).start();

            }
        });
    }
}
