package com.example.a18.path.constraintlayout;

import android.support.v4.app.Fragment;

import com.example.a18.path.TabViewPagerActivity;

public class ConstraintLayoutActivity extends TabViewPagerActivity {

    @Override
    public Fragment[] getFragments() {
        return new Fragment[]{
            new ConstrainSetFragment(),
            new ChainStyleFragment(),
        };
    }
}
