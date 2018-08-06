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
import kz.sgq.colorassistant.mvp.model.fragment.InfoModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.InfoModel
import kz.sgq.colorassistant.mvp.view.fragment.InfoView

@InjectViewState
class InfoPresenter : MvpPresenter<InfoView>() {
    private val model: InfoModel = InfoModelImpl()

    fun initColor(color: Int) {

        model.initModel(color)
        viewState.createLightness(model.getColor(), model.getLightness())
        viewState.createSaturation(model.getColor(), model.getSaturation())
    }

    fun init() {

//        viewState.installViewPager()
    }
}