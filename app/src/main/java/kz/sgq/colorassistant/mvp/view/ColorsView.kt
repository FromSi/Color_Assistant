package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.sgq.colorassistant.ui.util.ItemColor

interface ColorsView : MvpView {
    fun showLoadDB()
    fun showColorList()
    fun addItemsDB(colorList: MutableList<ItemColor>)
    fun clearItemsDB()
    fun updateItemsDB(index: Int)
    @StateStrategyType(SkipStrategy::class)
    fun showActivityInfo(list: MutableList<String>)
}