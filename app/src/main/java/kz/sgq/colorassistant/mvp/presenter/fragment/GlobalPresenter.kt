package kz.sgq.colorassistant.mvp.presenter.fragment

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.fragment.GlobalModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.GlobalModel
import kz.sgq.colorassistant.mvp.view.fragment.GlobalView
import kz.sgq.colorassistant.ui.util.ItemColor

@InjectViewState
class GlobalPresenter : MvpPresenter<GlobalView>() {
    private var model: GlobalModel = GlobalModelImpl()

    fun initPresenter() {

        viewState.showLoadDB()
        model.setReadListener(object : GlobalModelImpl.OnReadListener {
            override fun onRead(list: MutableList<ItemColor>) {

                viewState.showColorList()
                viewState.addList(list)
            }
        })
        model.initData()
    }

    fun handlerColorListener(itemCount: Int, lastVisibleItem: Int) {

        model.handlerColorListener(itemCount, lastVisibleItem)
    }

    fun setLike(id: Int, like: Boolean){

        model.setLike(id, like)
    }
}