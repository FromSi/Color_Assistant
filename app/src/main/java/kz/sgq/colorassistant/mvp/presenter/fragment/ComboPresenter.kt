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
import kz.sgq.colorassistant.mvp.model.fragment.ComboModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.ComboModel
import kz.sgq.colorassistant.mvp.view.fragment.ComboView

@InjectViewState
class ComboPresenter : MvpPresenter<ComboView>() {
    private var model: ComboModel = ComboModelImpl()

    fun initPresenter(list: MutableList<String>) {

        model.initColorList(list)
        viewState.init(model.getSize())
        initHeader()
        initColor()
        initColors()
    }

    fun handlerTextColor(index: Int) {

        viewState.setTextColor(model.getColorList()[index])
    }

    fun handlerBackgroundColor(index: Int) {

        viewState.setBackgroundColor(model.getColorList()[index])
    }

    fun getColor(index: Int): Int = model.getColorList()[index]

    private fun initHeader() {

        for (i in 0 until model.getSize())
            for (j in 0 until model.getSize())
                if (i + j < model.getSize())
                    viewState.initHeader(i, j, model.getColorList()[i + j])
    }

    private fun initColor() {

        for (i in 0 until model.getSize())
            viewState.initColor(i, model.getColorList()[i])
    }

    private fun initColors() {

        for (i in 0 until model.getSize())
            viewState.initColors(i, model.getNameType(), model.getValue()[i])
    }
}