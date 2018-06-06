package kz.sgq.colorassistant.mvp.presenter

import android.util.Log
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.infraestructure.networking.common.ControllerApi
import kz.sgq.colorassistant.infraestructure.networking.common.GsonConverter
import kz.sgq.colorassistant.mvp.model.ColorsModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.ColorsModel
import kz.sgq.colorassistant.mvp.view.ColorsView
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

@InjectViewState
class ColorsPresenter : MvpPresenter<ColorsView>() {
    private val model: ColorsModel = ColorsModelImpl()

    init {
        viewState.showLoadDB()
        getColors()
//        getColors(true)
    }

    fun handlerColorListener(itemCount: Int, lastVisibleItem: Int) {
        if (!model.isLoading() && itemCount <= lastVisibleItem + model.getVisibleThreshold()
        && lastVisibleItem != 0) {
            getColorList()
            model.setLoading(true)
        }
    }

    fun onItemLikeClick(view: View, id: Int, like: Boolean) {
        DataBaseRequest.updateColors(id, like)
        for (i in 0 until model.getIdList().size)
            if (model.getIdList()[i] == id) {
                model.setLike(i, like)
                return
            }
    }

    fun clear(){
        model.clear()
    }

    fun onItemViewClick(view: View, itemColor: ItemColor) {
        viewState.showActivityInfo(itemColor.colors)
    }

    private fun getColors() {
        DataBaseRequest.getColors()
                ?.subscribe({
                    if (it.size == 0) {
                        getAllColors()
                        ControllerApi.provider()
                                .updateCheck()
                    } else {
                        handlerColorList(it)
                        getUpdateCheck(it)
                    }
                })
    }

//    private fun getColors(like: Boolean) {
//        DataBaseRequest.getColors(like)
//                ?.subscribe({
//                    if (it.size == 0) {
//                        for (i in 0 until model.getIdList().size) {
//                            if (model.getLikeList()[i])
//                                viewState.updateItemsDB(i)
//                        }
//                    } else {
//                        for (i in 0 until model.getIdList().size) {
//                            if (model.getLikeList()[i])
//                                if (isHandlerColor(i, it))
//                                    viewState.updateItemsDB(i)
//                        }
//                    }
//                })
//    }

    private fun isHandlerColor(index: Int, colors: List<Colors>): Boolean {
        for (i in colors.indices) {
            if (model.getIdList()[index] == colors[i].idCol)
                return true
        }
        return false
    }

    private fun getAllColors() {
        ControllerApi.provider()
                .getAllColors()
                .subscribe({
                    DataBaseRequest.insertColors(GsonConverter
                            .convertColorsList(it))
                            .subscribe({
                                model.initRandom(it.size)
                                getColorList()
                                viewState.showColorList()
                                model.setLoading(false)
                            })
                }, {
                    getAllColors()
                })
    }

    private fun getColorList() {
        DataBaseRequest.getColors(model.getNumbers())
                ?.subscribe({
                    viewState.addItemsDB(model
                            .getItemColor(it))
                    model.setLoading(false)
                })
    }

    private fun handlerColorList(colors: MutableList<Colors>) {
        model.initRandom(colors.size)
        getColorList()
        viewState.showColorList()
        model.setLoading(false)
    }

    private fun getUpdateCheck(colors: MutableList<Colors>) {
        ControllerApi.provider()
                .updateCheck()
                .subscribe({
                    if (model.getCheck() < Integer.parseInt(it.check)) {
                        val check = Integer.parseInt(it.check)
                        DataBaseRequest.updateUpdate(model.getCheck(), check)
                        getUpdateColorList(model.getCheck())
                    } else {
                        handlerColorList(colors)
                    }
                },{})
    }

    private fun getUpdateColorList(check: Int) {
        ControllerApi.provider()
                .updateColors(check)
                .subscribe({
                    if (it.size != 0) {
                        DataBaseRequest.insertColors(GsonConverter
                                .convertColorsList(it)).subscribe({
//                            viewState.addItemsDB(model
//                                    .converterToItemColor(it))
                            model.setRandomSize(it.size)
                        })
                    }
                })
    }
}