package com.example.a18.path.scratchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class ScratchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        ScratchView viewById = (ScratchView) findViewById(R.id.sv);
        viewById.setImageResource(R.drawable.k);
    }

}
