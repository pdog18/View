package com.module_math

import kt.pdog18.com.base.BaseFragment
import kt.pdog18.com.base.TabViewPagerActivity

class MathActivity : TabViewPagerActivity() {
    override fun getFragments(): Array<BaseFragment> {

        return arrayOf(
            Math1Fragment()
        )
    }
}
