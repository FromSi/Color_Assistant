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

package kz.sgq.colorassistant.mvp.model.fragment.interfaces

import android.content.Intent
import kz.sgq.colorassistant.mvp.model.fragment.CloudModelImpl
import kz.sgq.colorassistant.room.table.Cloud

interface CloudModel {

    fun initItemList(initListener: CloudModelImpl.OnInitListener)

    fun calcColorList(cloud: Cloud): MutableList<String>

    fun calcShare(cloud: Cloud): String

    fun calcQRAnswer(data: Intent?): Cloud

    fun calcQRCode(resultCode: Int, data: Intent?): Boolean

    fun addItem(cloud: Cloud, eventListener: CloudModelImpl.OnEventListener)

    fun deleteItem(cloud: Cloud, eventListener: CloudModelImpl.OnEventListener)
}