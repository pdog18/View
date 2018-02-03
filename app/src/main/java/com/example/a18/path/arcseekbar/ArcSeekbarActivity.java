package com.example.a18.path.arcseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.a18.path.R;

public class ArcSeekbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_seekbar);


        ArcSeekbar seekbar = findViewById(R.id.dv);
        seekbar.setOnProgressChangeListener(percent -> Toast.makeText(ArcSeekbarActivity.this, "percent = " + percent, Toast.LENGTH_SHORT).show());

        findViewById(R.id.dv).postDelayed(() -> seekbar.setCellCount(5),3000);


        ArcView arcView = findViewById(R.id.arcseekbar2);


    }
}
