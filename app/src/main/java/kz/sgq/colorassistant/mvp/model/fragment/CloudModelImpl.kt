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

package kz.sgq.colorassistant.mvp.model.fragment

import kz.sgq.colorassistant.mvp.model.fragment.interfaces.CloudModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud

class CloudModelImpl : CloudModel {

    interface OnInitListener {

        fun onResult(list: MutableList<Cloud>)
    }

    override fun calcShare(cloud: Cloud): String {
        val text = StringBuffer("${cloud.colOne}${cloud.colTwo}${cloud.colThree}")

        if (cloud.colFour != null)
            text.append(cloud.colFour)

        if (cloud.colFive != null)
            text.append(cloud.colFive)

        return text.toString()
    }

    override fun deleteItem(cloud: Cloud, eventListener: DataBaseRequest.OnEventListener) {

        DataBaseRequest.deleteCloud(cloud, eventListener)
    }

    override fun initItemList(initListener: OnInitListener) {

        DataBaseRequest.getCloud()
                ?.subscribe { list: MutableList<Cloud>? -> initListener.onResult(list!!) }
    }

    override fun calcColorList(cloud: Cloud): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

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