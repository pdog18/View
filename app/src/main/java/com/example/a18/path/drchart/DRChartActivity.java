package com.example.a18.path.drchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a18.path.R;

public class DRChartActivity extends AppCompatActivity {

    private static final String TAG = "DRChartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drchart);

        final DRChartView viewById = (DRChartView) findViewById(R.id.dr);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: ");
                viewById.start();
            }
        });
    }
}
