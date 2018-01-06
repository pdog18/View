package com.example.a18.path.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a18.path.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CardHeightActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_height);

        editText = findViewById(R.id.et);

        List<String> data = new ArrayList<>();
        data.add("Android");
        data.add("Python");
        data.add("C#");
        data.add("Java");
        data.add("C++");
        data.add("Go");
        data.add("Object-C");
        data.add("JavaScript");
        data.add("C");
        final PickerView pv =  findViewById(R.id.pv);

        pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                pv.setAdapter(new PickViewAdapter<String>(data) {
                    @Override
                    protected View createView(ViewGroup parent, String o, int position) {
                        TextView textView = new TextView(parent.getContext());
                        textView.setText(o);
                        return textView;
                    }
                });
            }
        }, 5000);



        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(editText.getText().toString());
                Timber.d("position = %s", position);
                pv.setPosition(position);
            }
        });

    }
}
