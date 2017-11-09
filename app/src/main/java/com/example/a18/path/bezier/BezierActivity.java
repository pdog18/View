package com.example.a18.path.bezier;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a18.path.R;

public class BezierActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bezier2);

    }
}
