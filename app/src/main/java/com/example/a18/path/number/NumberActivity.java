package com.example.a18.path.number;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

public class NumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        final NumberAnim numberAnim = (NumberAnim) findViewById(R.id.na);
        numberAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberAnim.start();

            }
        });
    }
}
