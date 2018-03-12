package com.example.a18.path.materialdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;
import com.example.a18.path.materialdesign.widget.NumberTextView;

import butterknife.BindView;


public class NumberTextFragment extends BaseFragment {
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    NumberTextView tv_3;

    @BindView(R.id.tv_4)
    TextView tv_4;
    @BindView(R.id.tv_5)
    TextView tv_5;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_number_text;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_1.setText("111111");
        tv_2.setText("222222");
        tv_3.setNumber(3333.22f);
    }
}

