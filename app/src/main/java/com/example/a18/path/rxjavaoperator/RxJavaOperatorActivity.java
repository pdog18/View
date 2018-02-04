package com.example.a18.path.rxjavaoperator;

import android.support.v4.app.Fragment;

import com.example.a18.path.TabViewPagerActivity;
import com.example.a18.path.rxjavaoperator.operator.Just;

public class RxJavaOperatorActivity extends TabViewPagerActivity {

    @Override
    public Fragment[] getFragments() {
        return new Fragment[]{
            new Just()
        };
    }
}
