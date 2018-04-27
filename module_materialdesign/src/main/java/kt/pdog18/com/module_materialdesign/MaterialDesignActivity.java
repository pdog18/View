package kt.pdog18.com.module_materialdesign;

import android.support.v4.app.Fragment;

import kt.pdog18.com.base.TabViewPagerActivity;


public class MaterialDesignActivity extends TabViewPagerActivity {

    @Override
    public Fragment[] getFragments() {

        return new Fragment[]{
            new RippleFragment(),
            new NumberTextFragment()
        };

    }
}
