package com.example.a18.path.smartanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class SmartAnalysisActivity extends AppCompatActivity {

    @BindView(R.id.smartview)
    SmartChart smartChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_analysis);
        ButterKnife.bind(this);

        ChartDataHelper helper = new ChartDataHelper(MockSmartAnalysis.singleton(), smartChart);

        smartChart.setData(helper);

        smartChart.setGradeRangeChangeListener(new SmartChart.GradeRangeChangeListener() {
            @Override
            public void onGradeRangeChange(int last, int curr) {
                Timber.d("last = %s  , curr = %s", last ,curr);
            }
        });
    }

    @OnClick(R.id.btn1)
    void btn1() {

    }

    boolean aBoolean;
    @OnClick(R.id.btn2)
    void btn2() {
        smartChart.startRiskGradeRange(aBoolean ^= true);
    }

    @OnClick(R.id.btn3)
    void btn3() {

    }

    @OnClick(R.id.btn4)
    void btn4() {

    }
}
