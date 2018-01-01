package com.example.a18.path.text;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.example.a18.path.Utils.dp2px;

public class SmoothAdapter extends BaseSmoothAdapter<String> {
    public SmoothAdapter(List<String> list) {
        super(list);
    }

    @Override
    public View createView(ViewGroup viewGroup, String s, int index) {
        TextView textView = new TextView(viewGroup.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dp2px(50),dp2px(50));
        textView.setLayoutParams(params);
        textView.setText(s);
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
