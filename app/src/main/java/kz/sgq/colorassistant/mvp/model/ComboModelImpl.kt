package kz.sgq.colorassistant.mvp.model

import android.graphics.Color
import kz.sgq.colorassistant.mvp.model.interfaces.ComboModel
import kz.sgq.colorassistant.ui.util.ColorConverter

class ComboModelImpl : ComboModel {
    private var colorList: MutableList<Int> = arrayListOf()

    override fun getSize(): Int = colorList.size

    override fun getColorList(): MutableList<Int> = colorList

    override fun getNameType(): String = "Hex\nRGB\nHSV\nCMYK"

    override fun getValue(): MutableList<String> {
        val answerList: MutableList<String> = arrayListOf()
        for (i in 0 until colorList.size)
            answerList.add(ColorConverter
                    .getFullAnswer(colorList[i]))
        return answerList
    }

    override fun initList(colorList: MutableList<String>){
        for (i in 0 until colorList.size)
            this.colorList.add(Color
                    .parseColor(colorList[i]))
    }
}