/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.mvp.presenter.fragment

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.fragment.LikesModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.LikesModel
import kz.sgq.colorassistant.mvp.view.fragment.LikesView
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

@InjectViewState
class LikesPresenter : MvpPresenter<LikesView>() {
    private val model: LikesModel = LikesModelImpl()

    init {

        viewState.showLoadDB()
        DataBaseRequest.getColors(true)
                ?.subscribe {

                    if (it.size == 0) {

                        model.clearIdList()
                        viewState.clearItemsDB()
                        viewState.showLoadDB()
                    } else {

                        if (it.size > model.getColorList().size) {

                            for (i in 0 until it.size)
                                if (isCheck(it[i].idCol, model.getColorList().size))
                                    model.addIdList(it[i])
                        } else {

                            for (i in 0 until model.getColorList().size)
                                if (calcDelete(model.getColorList()[i].id, it)) {

                                    viewState.deleteItem(model.getColorList()[i].id)
                                    model.deleteIdList(i)

                                    break
                                }
                        }

                        viewState.showColorList()

                        if (model.getColorList().size <= 10)
                            model.setLoading(true)
                        else
                            model.setLoading(false)

                        getColorList()
                    }
                }
    }

    fun handlerColorListener(itemCount: Int, lastVisibleItem: Int) {

        if (!model.isLoading() &&
                (itemCount <= lastVisibleItem + model.getVisibleThreshold()) &&
                (lastVisibleItem != 0)) {

            getColorList()
            model.setLoading(true)
        }
    }

    fun onItemLikeClick(view: View, id: Int, like: Boolean) {

        DataBaseRequest.updateColors(id, like)
    }

    fun onItemViewClick(view: View, itemColor: ItemColor) {

        viewState.showActivityInfo(itemColor.colors)
    }

    private fun isCheck(id: Int, size: Int): Boolean {

        for (i in 0 until size)
            if (id == model.getColorList()[i].id)
                return false

        return true
    }

    private fun calcDelete(id: Int, list: MutableList<Colors>): Boolean {

        for (i in 0 until list.size)
            if (list[i].idCol == id)
                return false

        return true
    }

    private fun getColorList() {
        val intArray = model.getNumbers()

        for (i in 0 until intArray.size) {

            val item = intArray[i]
            viewState.addItemsDB(model.getColorList()[item])
        }

        model.setLoading(false)
    }
}