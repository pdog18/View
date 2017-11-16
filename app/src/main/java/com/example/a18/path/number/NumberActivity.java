package com.example.a18.path.number;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class NumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        final NumberAnim numberAnim = (NumberAnim) findViewById(R.id.na);
        numberAnim.post(new Runnable() {
            @Override
            public void run() {
                numberAnim.start();
            }
        });
    }
}
