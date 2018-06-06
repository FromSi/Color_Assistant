package kz.sgq.colorassistant.mvp.model.interfaces

interface ComboModel {
    fun getSize(): Int
    fun getColorList(): MutableList<Int>
    fun getNameType(): String
    fun getValue(): MutableList<String>
    fun initList(colorList: MutableList<String>)
}