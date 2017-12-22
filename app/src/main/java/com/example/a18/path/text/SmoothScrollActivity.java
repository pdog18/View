package com.example.a18.path.text;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a18.path.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmoothScrollActivity extends AppCompatActivity {


    @BindView(R.id.smooth)
    SmoothScrollLayout scrollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            TextView textView = new TextView(this);
            int color = random.nextInt(155) + 100;

            textView.setBackgroundColor(Color.rgb(color,color,color));
            textView.setText(i+ "");
            if (i % 2 == 0) {
                textView.setVisibility(View.GONE);
            }
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            scrollLayout.addView(textView);
        }


    }

    @OnClick(R.id.btn)
    void smooth() {
        scrollLayout.smoothTo(20);
    }

}

