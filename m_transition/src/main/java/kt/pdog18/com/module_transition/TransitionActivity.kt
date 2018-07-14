package kt.pdog18.com.module_transition

import kt.pdog18.com.base.BaseFragment
import kt.pdog18.com.base.TabViewPagerActivity

class TransitionActivity : TabViewPagerActivity() {

    override fun getFragments(): Array<BaseFragment> {
        return arrayOf(ConstrainSetFragment(), LifelineFragment(), AutoFragment(), ChangeBoundsFragment(), PathFragment(), SlideSample(), ScaleSample())
    }
}
