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

package kz.sgq.colorassistant.mvp.model

import kz.sgq.colorassistant.mvp.model.interfaces.CloudModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.interfaces.OnInitItemListener

class CloudModelImpl : CloudModel {

    override fun addItem(cloud: Cloud, eventListener: OnEventItemListener) {
        DataBaseRequest.insertCloud(cloud, eventListener)
    }

    override fun deleteItem(cloud: Cloud, eventListener: OnEventItemListener) {
        DataBaseRequest.deleteCloud(cloud, eventListener)
    }

    override fun initItemList(initListener: OnInitItemListener) {
        DataBaseRequest.getCloud()
                ?.subscribe { list: MutableList<Cloud>? -> initListener.answer(list!!) }
    }

    override fun initColorList(cloud: Cloud): MutableList<String> {
        val list: MutableList<String> = arrayListOf()

        list.add(cloud.colOne)
        list.add(cloud.colTwo)
        list.add(cloud.colThree)

        if (cloud.colFour != null)
            list.add(cloud.colFour!!)

        if (cloud.colFive != null)
            list.add(cloud.colFive!!)

        return list
    }
}