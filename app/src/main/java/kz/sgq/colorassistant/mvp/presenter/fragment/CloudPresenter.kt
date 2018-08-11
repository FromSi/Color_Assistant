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

import android.app.Activity
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.fragment.CloudModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.CloudModel
import kz.sgq.colorassistant.mvp.view.fragment.CloudView
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud

@InjectViewState
class CloudPresenter : MvpPresenter<CloudView>() {
    private val model: CloudModel = CloudModelImpl()

    fun initInitList() {

        model.initItemList(object : CloudModelImpl.OnInitListener {
            override fun onResult(list: MutableList<Cloud>) {

                viewState.initColorList(list)
            }
        })
    }

    fun initResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1)
            if (model.calcQRCode(resultCode, data))
                viewState.answerQR(model.calcQRAnswer(data))
            else
                viewState.errorQR()
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK)
            initInitList()
    }

    fun onItemViewClick(cloud: Cloud) {

        viewState.showActivityInfo(model.calcColorList(cloud))
    }

    fun onItemShareClick(cloud: Cloud) {

        viewState.shareItem(model.calcShare(cloud))
    }

    fun save(cloud: Cloud) {

        model.save(cloud, object : DataBaseRequest.OnEventListener {
            override fun onSuccess() {

                viewState.addItem(cloud)
            }

            override fun onError() {

            }
        })
    }

    fun onItemDeleteClick(cloud: Cloud, index: Int) {

        model.deleteItem(cloud, object : DataBaseRequest.OnEventListener {
            override fun onSuccess() {

                viewState.deleteItem(index)
            }

            override fun onError() {

            }
        })
    }
}