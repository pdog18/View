package kt.pdog18.com.core

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {
    val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun subscribe(){

    }

    fun unsubscribe() {
        compositeDisposable.clear()
    }
}