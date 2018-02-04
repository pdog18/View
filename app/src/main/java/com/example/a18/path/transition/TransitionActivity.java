package com.example.a18.path.transition;

import android.support.v4.app.Fragment;

import com.example.a18.path.TabViewPagerActivity;

public class TransitionActivity extends TabViewPagerActivity {


    @Override
    public Fragment[] getFragments() {
        return new Fragment[]{
            new ConstrainSetFragment(),
            new LifelineFragment(),
            new AutoFragment(),
            new ChangeBoundsFragment(),
            new PathFragment(),
            new SlideSample(),
            new ScaleSample(),
        };
    }
}
