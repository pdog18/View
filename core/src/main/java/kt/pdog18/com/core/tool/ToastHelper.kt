package kt.pdog18.com.core.tool

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast


object ToastHelper {
    private lateinit var mToast: Toast

    @SuppressLint("ShowToast")
    fun init(context: Context) {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun toast(text: CharSequence?, duration: Int) {
        text?.let {
            mToast.let {
                onMainThread {
                    mToast.setText(text)
                    mToast.duration = duration
                    mToast.show()
                }
            }
        }
    }

    fun toast(context: Context, resId: Int, duration: Int) {
        toast(context.getText(resId), duration)
    }
}

