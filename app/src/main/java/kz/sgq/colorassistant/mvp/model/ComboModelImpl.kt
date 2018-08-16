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
import kz.sgq.colorassistant.mvp.model.interfaces.ComboModel
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.util.HSLConverter
import kz.sgq.colorassistant.ui.util.ItemDetails

class ComboModelImpl : ComboModel {
    private var colorList: MutableList<Int> = mutableListOf()

    private lateinit var share: String

    override fun getSize(): Int = colorList.size

    override fun getShare(): String = share

    override fun getColor(index: Int): Int = colorList[index]

    override fun getValue(index: Int): String = ColorConverter.getFullAnswer(colorList[index])

    override fun getSaturation(index: Int): MutableList<ItemDetails> = mutableListOf<ItemDetails>()
            .apply {
                val saturation = HSLConverter.getSaturationList(getColor(index))

                for (i in saturation.indices)
                    this.add(ItemDetails(
                            ColorConverter.getFullAnswer(saturation[i]),
                            saturation[i]
                    ))
            }


    override fun getLightness(index: Int): MutableList<ItemDetails> = mutableListOf<ItemDetails>()
            .apply {
                val lightness = HSLConverter.getLightnessList(getColor(index))

                for (i in lightness.indices)
                    this.add(ItemDetails(
                            ColorConverter.getFullAnswer(lightness[i]),
                            lightness[i]
                    ))
            }


    override fun initColorList(list: MutableList<String>) {

        for (i in 0 until list.size)
            this.colorList.add(Color.parseColor(list[i]))

        calcShare()
    }

    private fun calcShare() {
        val text = StringBuffer()

        for (i in 0 until colorList.size)
            text.append(colorList[i])

        share = text.toString()
    }
}