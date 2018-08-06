package kz.sgq.colorassistant.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.ComboModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.ComboModel
import kz.sgq.colorassistant.mvp.view.ComboView

@InjectViewState
class ComboPresenter : MvpPresenter<ComboView>() {
    private val model: ComboModel = ComboModelImpl()

    fun initList(list: MutableList<String>) {

        model.initList(list)
        viewState.initFragment(model.getList())
    }

    fun showShare() {

        viewState.showShare(model.getTextShare())
    }
}