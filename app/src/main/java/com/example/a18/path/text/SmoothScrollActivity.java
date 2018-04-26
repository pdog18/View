package com.example.a18.path.text;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a18.path.R;

import java.util.Arrays;


public class SmoothScrollActivity extends AppCompatActivity {


    SmoothScrollLayout scrollLayout;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);

        editText = findViewById(R.id.et);
        scrollLayout = findViewById(R.id.smooth);

        findViewById(R.id.btn).setOnClickListener(v -> {
            String s = editText.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
            }

            scrollLayout.smoothTo(Integer.valueOf(s));
        });

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
}

