package com.example.a18.path.materialdesign;

import android.support.v4.app.Fragment;

import com.example.a18.path.TabViewPagerActivity;

public class MaterialDesignActivity extends TabViewPagerActivity {

    @Override
    public Fragment[] getFragments() {

        return new Fragment[]{
            new RippleFragment(),
            new NumberTextFragment()
        };

    }
}
