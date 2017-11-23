package com.example.a18.path.jxyview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

public class JxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxy);

        JXYView jxyView = (JXYView) findViewById(R.id.jxyview);
        jxyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jxyView.setText("是个大撒公司等机构上帝搞好");
            }
        });
    }
}
