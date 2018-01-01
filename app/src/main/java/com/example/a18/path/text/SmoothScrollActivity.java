package com.example.a18.path.text;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a18.path.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmoothScrollActivity extends AppCompatActivity {


    @BindView(R.id.smooth)
    SmoothScrollLayout scrollLayout;
    @BindView(R.id.et)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);

        String[] strings = {"333",
            "222",
            "4444",
            "45325",
            "44123544",
            "44123544",
            "4421544",
            "666asd6",
            "9999",};
        SmoothAdapter adapter = new SmoothAdapter(Arrays.asList(strings));

        scrollLayout.setAdapter(adapter);
//        scrollLayout.setData(Arrays.asList(strings));

    }

    @OnClick(R.id.btn)
    void smooth() {
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
        }

        scrollLayout.smoothTo(Integer.valueOf(s));


    }


    @OnClick(R.id.print)
    void print() {

    }

}

