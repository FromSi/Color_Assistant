package kz.sgq.colorassistant.mvp.presenter

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.LikesModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.LikesModel
import kz.sgq.colorassistant.mvp.view.LikesView
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.ui.util.ItemColor

@InjectViewState
class LikesPresenter : MvpPresenter<LikesView>() {
    private val model: LikesModel = LikesModelImpl()

    init {
        viewState.showLoadDB()
        DataBaseRequest.getColors(true)
                ?.subscribe({
                    if (it.size == 0) {
                        viewState.clearItemsDB()
                        viewState.showLoadDB()
                    } else {
                        viewState.clearItemsDB()
                        viewState.addItemsDB(model
                                .getItemColor(it))
                        viewState.showColorList()
                    }
                })
    }

    fun onItemLikeClick(view: View, id: Int, like: Boolean) {
        DataBaseRequest.updateColors(id, like)
    }

    fun onItemViewClick(view: View, itemColor: ItemColor) {
        viewState.showActivityInfo(itemColor.colors)
    }
}