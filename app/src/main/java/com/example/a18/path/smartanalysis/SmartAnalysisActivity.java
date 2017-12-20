package com.example.a18.path.smartanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartAnalysisActivity extends AppCompatActivity {

    @BindView(R.id.smartview)
    SmartChart smartChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_analysis);
        ButterKnife.bind(this);
        smartChart.setData(new MockChartData());


    }
}
