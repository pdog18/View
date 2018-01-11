package com.example.a18.path.viewpagertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a18.path.ColorUtil;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ViewPagerFragmentActivity extends AppCompatActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);
        ButterKnife.bind(this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    static class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyVFragment myVFragment = new MyVFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            myVFragment.setArguments(bundle);
            return myVFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class MyVFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(container.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setBackgroundColor(ColorUtil.getRandColorCode());


            int position = getArguments().getInt("position");

            Timber.d("textView = %s  position = %s", textView,position);
            return textView;
        }
    }

}
