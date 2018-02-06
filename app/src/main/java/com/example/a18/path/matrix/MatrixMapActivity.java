package com.example.a18.path.matrix;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a18.path.R;

import java.util.ArrayList;
import java.util.List;

public class MatrixMapActivity extends AppCompatActivity {

    List<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_map);

        View tv_scale_x = findViewById(R.id.tv_scale_x);
        View tv_scale_y = findViewById(R.id.tv_scale_y);
        View tv_skew_x = findViewById(R.id.tv_skew_x);
        View tv_skew_y = findViewById(R.id.tv_skew_y);
        View tv_trans_x = findViewById(R.id.tv_trans_x);
        View tv_trans_y = findViewById(R.id.tv_trans_y);
        View tv_persp_0 = findViewById(R.id.tv_persp_0);
        View tv_persp_1 = findViewById(R.id.tv_persp_1);
        View tv_persp_2 = findViewById(R.id.tv_persp_2);


        viewList.add(tv_persp_0);
        viewList.add(tv_persp_1);
        viewList.add(tv_persp_2);
        viewList.add(tv_scale_x);
        viewList.add(tv_scale_y);
        viewList.add(tv_skew_x);
        viewList.add(tv_skew_y);
        viewList.add(tv_trans_x);
        viewList.add(tv_trans_y);

        for (View view : viewList) {
            ((TextView) view).setGravity(Gravity.CENTER);
        }

        RadioGroup radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (View view : viewList) {
                    view.setBackground(null);
                }
                switch (checkedId) {
                    case R.id.rd_mark:
                        tv_scale_x.setBackgroundColor(Color.argb(255, 244, 154, 43));
                        tv_scale_y.setBackgroundColor(Color.argb(255, 244, 154, 43));
                        tv_skew_x.setBackgroundColor(Color.argb(255, 244, 154, 43));
                        tv_skew_y.setBackgroundColor(Color.argb(255, 244, 154, 43));

                        break;
                    case R.id.rd_scale:
                        tv_scale_x.setBackgroundColor(Color.rgb(141, 109, 62));
                        tv_scale_y.setBackgroundColor(Color.rgb(141, 109, 62));

                        break;
                    case R.id.rd_perspective:
                        tv_persp_0.setBackgroundColor(Color.rgb(194, 243, 53));
                        tv_persp_1.setBackgroundColor(Color.rgb(194, 243, 53));
                        tv_persp_2.setBackgroundColor(Color.rgb(194, 243, 53));

                        break;
                    case R.id.rd_skew:
                        tv_skew_x.setBackgroundColor(Color.rgb(135, 137, 61));
                        tv_skew_y.setBackgroundColor(Color.rgb(135, 137, 61));
                        break;
                    case R.id.rd_translate:
                        tv_trans_x.setBackgroundColor(Color.rgb(34, 172, 242));
                        tv_trans_y.setBackgroundColor(Color.rgb(34, 172, 242));
                        break;
                }
            }
        });

        ((RadioButton) findViewById(R.id.rd_perspective)).setChecked(true);

    }
}
