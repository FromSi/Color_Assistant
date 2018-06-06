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