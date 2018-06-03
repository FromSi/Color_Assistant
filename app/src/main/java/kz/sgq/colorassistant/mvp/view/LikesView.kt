package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.ui.util.ItemColor

interface LikesView : MvpView {
    fun showNoItemText()
    fun clearItemsDB()
    fun showLoadDB()
    fun showColorList()
    fun addItemsDB(colorList: MutableList<ItemColor>)
    fun showActivityInfo(list: MutableList<String>)
}