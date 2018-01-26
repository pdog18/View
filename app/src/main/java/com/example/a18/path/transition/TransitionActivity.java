package com.example.a18.path.transition;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransitionActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    Fragment[] fragments = {
        new LifelineFragment(),
        new AutoFragment(),
        new ChangeBoundsFragment(),
        new PathFragment(),
        new SlideSample(),
        new ScaleSample()
    };
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

     class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

         @Override
         public CharSequence getPageTitle(int position) {
             return fragments[position].getClass().getSimpleName();
         }

         @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
