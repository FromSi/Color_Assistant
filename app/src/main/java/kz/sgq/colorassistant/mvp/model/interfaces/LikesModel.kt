package kz.sgq.colorassistant.mvp.model.interfaces

import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

interface LikesModel {
    fun addIdList(item: Colors)
    fun deleteIdList(id: Int)
    fun clearIdList()
    fun getColorList(): MutableList<ItemColor>
    fun getNumbers(): IntArray
    fun isLoading(): Boolean
    fun setLoading(loading: Boolean)
    fun setRandomSize(size: Int)
    fun getVisibleThreshold(): Int
}