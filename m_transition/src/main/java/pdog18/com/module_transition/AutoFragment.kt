package pdog18.com.module_transition

import android.os.Bundle
import androidx.transition.TransitionManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_auto.*
import pdog18.com.base.BaseFragment
import pdog18.com.base.Layout

@Layout(R.layout.fragment_auto)
class AutoFragment : BaseFragment() {

    private var visible: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_auto
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            TransitionManager.beginDelayedTransition(transitions_container)
            visible = !visible
            text.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}
