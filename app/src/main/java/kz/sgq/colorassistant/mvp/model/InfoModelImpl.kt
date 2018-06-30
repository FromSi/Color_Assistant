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

import kz.sgq.colorassistant.mvp.model.interfaces.InfoModel
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.util.HSLConverter
import kz.sgq.colorassistant.ui.util.ItemDetails

class InfoModelImpl : InfoModel {
    private var saturationList: MutableList<ItemDetails> = arrayListOf()
    private var lightnessList: MutableList<ItemDetails> = arrayListOf()

    private var color: Int = 0

    override fun create(color: Int) {
        this.color = color
        val saturation = HSLConverter.getSaturationList(color)
        val lightness = HSLConverter.getLightnessList(color)
        clearAllList()

        for (i in saturation.indices) {
            saturationList
                    .add(ItemDetails(ColorConverter
                            .getFullAnswer(saturation[i]),
                            saturation[i]))
        }

        for (i in lightness.indices) {
            lightnessList
                    .add(ItemDetails(ColorConverter
                            .getFullAnswer(lightness[i]),
                            lightness[i]))
        }
    }

    override fun getSaturation(): MutableList<ItemDetails> = saturationList

    override fun getLightness(): MutableList<ItemDetails> = lightnessList

    override fun getColor(): Int = color

    private fun clearAllList(){
        saturationList.clear()
        lightnessList.clear()
    }
}