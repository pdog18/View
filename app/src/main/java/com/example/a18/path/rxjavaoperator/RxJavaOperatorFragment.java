package com.example.a18.path.rxjavaoperator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;


public abstract class RxJavaOperatorFragment extends BaseFragment {
    @BindView(R.id.tv_output)
    TextView tv_output;
    @BindView(R.id.tv_code)
    TextView tv_code;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rxjava_operator;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_code.setText(getString(getCodeStringId()));
    }

    protected abstract int getCodeStringId();

    @OnClick(R.id.btn_click_operator)
    void btn_click_operator() {
        doOnClick(tv_output);
    }

    protected abstract void doOnClick(TextView textView);
}
