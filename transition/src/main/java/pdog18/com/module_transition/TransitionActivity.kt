package pdog18.com.module_transition

import pdog18.com.base.BaseFragment
import pdog18.com.base.TabViewPagerActivity

class TransitionActivity : TabViewPagerActivity() {

    override fun getFragments(): Array<BaseFragment> {
        return arrayOf(ConstrainSetFragment(), LifelineFragment(), AutoFragment(), ChangeBoundsFragment(), PathFragment(), SlideSample(), ScaleSample())
    }
}
