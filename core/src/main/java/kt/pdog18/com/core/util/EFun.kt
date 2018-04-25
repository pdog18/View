package kt.pdog18.com.core.util

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kt.pdog18.com.core.tool.ToastHelper

fun Disposable.into(cp: CompositeDisposable) {
    cp.add(this)
}

fun Activity.toast(text: CharSequence?) {
    ToastHelper.toast(text, Toast.LENGTH_SHORT)
}

fun Fragment.toast(text: CharSequence?) {
    this.context?.let {
        ToastHelper.toast(text, Toast.LENGTH_SHORT)
    }
}

fun ImageView.load(url: String?) {
    url?.let {
        Glide.with(this.context)
                .load(url)
                .into(this)
    }
}