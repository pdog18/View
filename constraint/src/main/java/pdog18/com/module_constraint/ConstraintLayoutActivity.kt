package pdog18.com.module_constraint

import pdog18.com.base.BaseFragment
import pdog18.com.base.TabViewPagerActivity

class ConstraintLayoutActivity : TabViewPagerActivity() {
    override fun getFragments(): Array<BaseFragment> {
        return arrayOf(
            BottomFragment(),
            ChainStyle6Fragment(),
            ConstrainSet2Fragment(),
            ConstrainSetFragment(),
            ChainStyle5Fragment(),
            ChainStyleFragment(),
            ChainStyle2Fragment(),
            ChainStyle3Fragment(),
            ChainStyle4Fragment()
        )
    }
}
