package kz.sgq.colorassistant.mvp.model.interfaces

import kz.sgq.colorassistant.infraestructure.networking.gson.ColorsGson
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

interface ColorsModel {
    fun initRandom(size: Int)
    fun getNumbers(): IntArray
    fun getCheck(): Int
    fun getIdList(): MutableList<Int>
    fun getLikeList(): MutableList<Boolean>
    fun setLike(index: Int, like: Boolean)
    fun getItemColor(colors: MutableList<Colors>): MutableList<ItemColor>
    fun isLoading(): Boolean
    fun setLoading(loading: Boolean)
    fun setRandomSize(size: Int)
    fun getVisibleThreshold(): Int
    fun converterToItemColor(list: MutableList<ColorsGson>): MutableList<ItemColor>
}