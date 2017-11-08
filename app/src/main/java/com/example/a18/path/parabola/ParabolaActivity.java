package com.example.a18.path.parabola;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a18.path.R;

public class ParabolaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parabola);
        findViewById(R.id.pv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ParabolaView) v).start();
                System.out.println("start");
            }
        });
    }
}
