package kt.pdog18.com.core

abstract class MvpActivity<out P : BasePresenter> : BaseActivity(), BaseView<P> {

    override fun startCreate(){
        presenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}