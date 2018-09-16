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

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.MainModel
import kz.sgq.colorassistant.mvp.view.MainView
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.fragment.CloudFragment
import kz.sgq.colorassistant.ui.util.CodeActivity

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

    fun save(cloud: Cloud) {

        model.save(cloud, object : DataBaseRequest.OnEventListener {
            override fun onSuccess() {
                if (model.getFragment() is CloudFragment)
                    (model.getFragment() as CloudFragment).addItem(cloud)
            }

            override fun onError() {

            }
        })
    }

    fun setFragment(fragment: Fragment) {

        model.setFragment(fragment)
    }

    fun initResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CodeActivity.QR_SCAN.ID)
            if (model.calcQRCode(resultCode, data))
                viewState.answerQR(model.calcQRAnswer(data))
            else
                viewState.errorQR()
        else if (requestCode == CodeActivity.IMAGE_SCAN.ID && resultCode == Activity.RESULT_OK) {

        } else if (requestCode == CodeActivity.CONSTRUCTOR.ID && resultCode == Activity.RESULT_OK) {

        }
    }

    fun handlerLink(data: String){
        viewState.answerLink(model.calcLinkAnswer(data))
    }

    fun openMenu() {

        viewState.openMenu(model.getCurrentFragment())
    }

    fun menuClick(fragmentCurrent: MainModelImpl.MainFragment) {

        if (fragmentCurrent != model.getCurrentFragment()){

            model.setCurrentFragment(fragmentCurrent)

            when (model.getCurrentFragment()) {
                MainModelImpl.MainFragment.GLOBAL -> {

                    viewState.global()
                }
                MainModelImpl.MainFragment.CLOUD -> {

                    viewState.cloud()
                }
                else -> {

                    viewState.global()
                }
            }
        }
    }

    fun setCurrentFragment(fragmentCurrent: MainModelImpl.MainFragment) {

        model.setCurrentFragment(fragmentCurrent)
    }
}