package com.example.a18.path.span;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.example.a18.path.*;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpanActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);
        ButterKnife.bind(this);

        SpannableStringBuilder spannableStringBuilder = new SpanUtils().append("国").setFontSize(128, true)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_TOP)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_BASELINE)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_BOTTOM)
            .append("小小小").setFontSize(12, true)
            .create();

        tv.setText(spannableStringBuilder);

    }
}
