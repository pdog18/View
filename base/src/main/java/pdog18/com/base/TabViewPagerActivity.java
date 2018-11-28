package pdog18.com.base;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;

public abstract class TabViewPagerActivity extends AppCompatActivity {

    TabLayout tabLayout;

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_viewpager);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);


        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),getFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(MODE_SCROLLABLE);
    }

    public abstract Fragment[] getFragments();

    class MyAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;
        public MyAdapter(FragmentManager fm,Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
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
