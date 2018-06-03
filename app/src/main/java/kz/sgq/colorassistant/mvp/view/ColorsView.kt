package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.ui.util.ItemColor

interface ColorsView : MvpView {
    fun showLoadDB()
    fun showColorList()
    fun addItemsDB(colorList: MutableList<ItemColor>)
    fun clearItemsDB()
    fun updateItemsDB(index: Int)
    fun showActivityInfo(list: MutableList<String>)
}