package com.example.a18.path.rxjavaoperator.operator;


import android.widget.TextView;

import com.example.a18.path.R;
import com.example.a18.path.rxjavaoperator.RxJavaOperatorFragment;

import io.reactivex.Observable;

public class Just extends RxJavaOperatorFragment {
    @Override
    protected int getCodeStringId() {
        return R.string.operator_just;
    }

    @Override
    protected void doOnClick(TextView textView) {

        Observable.just("test")
            .subscribe(textView::setText);
    }
}
