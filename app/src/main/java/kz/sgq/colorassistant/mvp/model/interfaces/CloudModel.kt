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

package kz.sgq.colorassistant.mvp.model.interfaces

import android.content.Intent
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.interfaces.OnInitItemListener

interface CloudModel {
    fun initItemList(initListener: OnInitItemListener)
    fun initColorList(cloud: Cloud): MutableList<String>
    fun calcShare(cloud: Cloud): String
    fun parseQRAnswer(data: Intent?): Cloud
    fun calcQRCode(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    fun addItem(cloud: Cloud, eventListener: OnEventItemListener)
    fun deleteItem(cloud: Cloud, eventListener: OnEventItemListener)
}