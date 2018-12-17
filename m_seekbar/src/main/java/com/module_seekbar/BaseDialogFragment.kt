package com.module_seekbar

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import com.pdog.dimension.dp

/**
 * 全屏的dialogfragment
 */
abstract class BaseDialogFragment : androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        with(dialog.window) {
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
            // 总之达不到想要的效果
            setBackgroundDrawableResource(android.R.color.transparent)
        }


        val root = dialog.layoutInflater.inflate(getLayoutId(), null)
//        root.animation = AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top)
        root.animation = TranslateAnimation(0f, 0f, 360f.dp, 0f).apply {
            duration = 200
        }
        initView(root)
        dialog.setContentView(root)
        // 这句话起全屏的作用
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)

        return dialog
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(root: View)
}
