package kz.sgq.colorassistant.mvp.model.interfaces

import kz.sgq.colorassistant.ui.util.ItemDetails

interface InfoModel {
    fun create(color: Int)
    fun getColor(): Int
    fun getSaturation(): MutableList<ItemDetails>
    fun getLightness(): MutableList<ItemDetails>
}