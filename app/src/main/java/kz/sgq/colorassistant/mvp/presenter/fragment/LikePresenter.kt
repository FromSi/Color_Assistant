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

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.fragment.LikeModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.LikeModel
import kz.sgq.colorassistant.mvp.view.fragment.LikeView
import kz.sgq.colorassistant.ui.util.ItemColor

@InjectViewState
class LikePresenter : MvpPresenter<LikeView>() {
    private val model: LikeModel = LikeModelImpl()

    fun initPresenter() {

        viewState.showLoadDB()
        model.loadDB(object : LikeModelImpl.OnAnswerListener {
            override fun onAnswer(list: MutableList<ItemColor>) {

                if (list.size != 0)
                    viewState.showColorList()
                else
                    viewState.showLoadDB()

                viewState.setList(list)
            }
        })
    }

    fun setLike(id: Int, like: Boolean) {

        model.updateColor(id, like)
    }
}