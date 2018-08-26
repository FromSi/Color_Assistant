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

import android.net.Uri
import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.room.table.Cloud

interface ImageView : MvpView {

    fun initImage(photoUri: Uri)

    fun initItemsColor(list: MutableList<MutableList<Int>>)

    fun finishActivity()
}