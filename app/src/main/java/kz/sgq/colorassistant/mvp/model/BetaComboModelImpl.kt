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

import android.graphics.Color
import kz.sgq.colorassistant.mvp.model.interfaces.BetaComboModel
import kz.sgq.colorassistant.ui.util.ColorConverter

class BetaComboModelImpl : BetaComboModel {
    private var colorList: MutableList<Int> = arrayListOf()

    override fun getSize(): Int = colorList.size

    override fun getColor(index: Int): Int = colorList[index]

    override fun getValue(index: Int): String = ColorConverter.getFullAnswer(colorList[index])

    override fun initColorList(list: MutableList<String>) {

        for (i in 0 until list.size)
            this.colorList.add(Color.parseColor(list[i]))
    }
}