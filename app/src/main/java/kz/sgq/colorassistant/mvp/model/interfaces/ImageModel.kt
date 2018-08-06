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

import android.graphics.Bitmap
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.interfaces.OnClickListener

interface ImageModel {

    fun getColorList(): MutableList<MutableList<Int>>

    fun getState(): Boolean

    fun getCloud(index: Int): Cloud

    fun setCurrentImage(currentImage: Bitmap, click: OnClickListener)

    fun setState(state: Boolean)

    fun saveCloud(index: Int)

    fun deleteCloud(index: Int)
}