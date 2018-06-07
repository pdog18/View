package kt.pdog18.com.module_constraint

import kt.pdog18.com.base.BaseFragment
import kt.pdog18.com.base.TabViewPagerActivity

class ConstraintLayoutActivity : TabViewPagerActivity() {
    override fun getFragments(): Array<BaseFragment> {
        return arrayOf(
            ConstrainSet2Fragment(),
            ConstrainSetFragment(),
            ChainStyleFragment(),
            ChainStyle2Fragment(),
            ChainStyle3Fragment(),
            ChainStyle4Fragment()
        )
    }
}
