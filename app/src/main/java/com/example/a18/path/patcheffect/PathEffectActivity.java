package com.example.a18.path.patcheffect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

public class PathEffectActivity extends AppCompatActivity {

    private PathEffectView pathEffectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_effect);

        pathEffectView = findViewById(R.id.pev);
        pathEffectView.post(new Runnable() {
            @Override
            public void run() {
pathEffectView.start();
            }
        });

    }
}
