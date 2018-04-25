package kt.pdog18.com.core

interface BaseView<out P : BasePresenter> {
    val presenter: P
}