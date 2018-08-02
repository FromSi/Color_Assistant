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

import android.app.Activity
import android.content.Intent
import kz.sgq.colorassistant.mvp.model.interfaces.CloudModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.interfaces.OnInitItemListener

class CloudModelImpl : CloudModel {
    override fun calcQRCode(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == 10) {
                val scanResult = data!!.getStringExtra("scan_result")
                val size = scanResult.length
                if ((size == 21) || (size == 28) || (size == 35))
                    return true
            }
        return false
    }

    override fun parseQRAnswer(data: Intent?): Cloud {
        val scanResult = data!!.getStringExtra("scan_result")
        val cloud = Cloud(
                scanResult.substring(0, 7),
                scanResult.substring(7, 14),
                scanResult.substring(14, 21)
        )

        if (scanResult.length >= 28)
            cloud.colFour = scanResult.substring(21, 28)

        if (scanResult.length >= 35)
            cloud.colFive = scanResult.substring(28, 35)

        return cloud
    }

    override fun calcShare(cloud: Cloud): String {
        val text = StringBuffer("${cloud.colOne}${cloud.colTwo}${cloud.colThree}")

        if (cloud.colFour != null)
            text.append(cloud.colFour)

        if (cloud.colFive != null)
            text.append(cloud.colFive)

        return text.toString()
    }

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