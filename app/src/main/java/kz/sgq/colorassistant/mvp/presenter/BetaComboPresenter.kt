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

package kz.sgq.colorassistant.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.BetaComboModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.BetaComboModel
import kz.sgq.colorassistant.mvp.view.BetaComboView

@InjectViewState
class BetaComboPresenter : MvpPresenter<BetaComboView>() {
    private val model: BetaComboModel = BetaComboModelImpl()

    fun initColorList(list: MutableList<String>) {

        model.initColorList(list)
        viewState.handlerVisibly(model.getSize())
        viewState.initClicks(model.getSize())
        initHeader()
        initItemBackground()
        initItemValue()
    }

    fun handlerTextColor(index: Int) {

        viewState.setTextColor(model.getColor(index))
    }

    fun handlerBackgroundColor(index: Int) {

        viewState.setBackgroundColor(model.getColor(index))
    }

    private fun initItemValue(){

        for (i in 0 until model.getSize())
            viewState.initItemValue(i, model.getValue(i))
    }

    private fun initHeader() {

        for (i in 0 until model.getSize())
            for (j in 0 until model.getSize())
                if (i + j < model.getSize())
                    viewState.initHeader(i, j, model.getColor(i + j))
    }

    private fun initItemBackground() {

        for (i in 0 until model.getSize())
            viewState.initItemBackground(i, model.getColor(i))
    }
}