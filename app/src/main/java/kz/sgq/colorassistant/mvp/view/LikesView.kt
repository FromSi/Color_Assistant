package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.sgq.colorassistant.ui.util.ItemColor

interface LikesView : MvpView {
    fun clearItemsDB()
    fun showLoadDB()
    fun showColorList()
    fun deleteItem(id: Int)
    fun addItemsDB(item: ItemColor)
    @StateStrategyType(SkipStrategy::class)
    fun showActivityInfo(list: MutableList<String>)
}