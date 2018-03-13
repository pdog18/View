package com.example.a18.path.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;
import com.pdog18.processor_lib.BindLayout;


public class AnnotationActivity extends AppCompatActivity {

    @BindLayout(R.layout.activity_activity)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }
}
