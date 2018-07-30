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

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.CloudModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.CloudModel
import kz.sgq.colorassistant.mvp.view.CloudView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.interfaces.OnInitItemListener

@InjectViewState
class CloudPresenter : MvpPresenter<CloudView>() {
    private val model: CloudModel = CloudModelImpl()

    fun initInitList() {

        model.initItemList(object : OnInitItemListener {
            override fun answer(list: MutableList<Cloud>) {
                viewState.initColorList(list)
            }
        })
    }

    fun onItemViewClick(cloud: Cloud){

        viewState.showActivityInfo(model.initColorList(cloud))
    }

    fun onItemShareClick(cloud: Cloud){

    }

    fun onItemDeleteClick(cloud: Cloud, index: Int){
        model.deleteItem(cloud, object : OnEventItemListener {

            override fun success() {
                viewState.deleteItem(index)
            }

            override fun error() {
                Log.d("TestAddItemInRoomORM", "Error adding item")
            }
        })
    }

    fun addItem(cloud: Cloud) {
        model.addItem(cloud, object : OnEventItemListener {

            override fun success() {
                viewState.addItem(cloud)
            }

            override fun error() {
                Log.d("TestAddItemInRoomORM", "Error adding item")
            }
        })
    }
}