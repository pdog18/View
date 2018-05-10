package kt.pdog18.com.module_transition

import android.os.Bundle
import android.support.transition.TransitionManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_auto.*

import kt.pdog18.com.base.BaseFragment


class AutoFragment : BaseFragment() {

    private var visible: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_auto
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            TransitionManager.beginDelayedTransition(transitions_container)
            text.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}
