package com.example.a18.path.scale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a18.path.R;

public class ScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale2);
        final ScaleFrame viewById = (ScaleFrame) findViewById(R.id.fr);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("sout ");
                viewById.setScale(2,2);
            }
        });


        final ScaleView viewById1 = (ScaleView) findViewById(R.id.sv);
        viewById1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewById1.setScale(2,2);
            }
        });
    }
}
