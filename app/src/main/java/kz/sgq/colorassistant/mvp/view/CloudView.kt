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

package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.room.table.Cloud

interface CloudView : MvpView {
    fun initColorList(list: MutableList<Cloud>)
    fun addItem(cloud: Cloud)
    fun deleteItem(index: Int)
    fun showActivityInfo(list: MutableList<String>)
}