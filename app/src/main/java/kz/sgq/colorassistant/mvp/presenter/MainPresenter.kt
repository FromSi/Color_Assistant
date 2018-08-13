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
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.MainModel
import kz.sgq.colorassistant.mvp.view.MainView

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private val model: MainModel = MainModelImpl()

    fun fabClick() {

        when (model.getCurrentFragment()) {
            MainModelImpl.MainFragment.GLOBAL -> {

                viewState.like()
            }
            MainModelImpl.MainFragment.LIKE -> {

                viewState.global()
            }
            MainModelImpl.MainFragment.CLOUD -> {

                viewState.constructor()
            }
        }
    }

    fun openMenu() {

        viewState.openMenu(model.getCurrentFragment())
    }

    fun menuClick() {

        when (model.getCurrentFragment()) {
            MainModelImpl.MainFragment.GLOBAL, MainModelImpl.MainFragment.LIKE -> {

                viewState.cloud()
            }
            MainModelImpl.MainFragment.CLOUD -> {

                viewState.global()
            }
        }
    }

    fun setCurrentFragment(fragmentCurrent: MainModelImpl.MainFragment) {

        model.setCurrentFragment(fragmentCurrent)
    }
}