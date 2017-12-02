package com.example.a18.path.arcseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a18.path.R;

public class ArcSeekbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_seekbar);


        ((DragView) findViewById(R.id.dv)).setOnProgressChangeListener(new DragView.ProgressChangeListener() {
            @Override
            public void onProgressChange(float percent) {
                Toast.makeText(ArcSeekbarActivity.this, "percent = " + percent, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
