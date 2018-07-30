package com.module_math

import pdog18.com.base.BaseFragment
import pdog18.com.base.TabViewPagerActivity

class MathActivity : TabViewPagerActivity() {
    override fun getFragments(): Array<BaseFragment> {

        return arrayOf(
            Math1Fragment()
        )
    }
}
