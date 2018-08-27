package kz.sgq.colorassistant.mvp.view.fragment

import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.ui.util.ItemColor

interface GlobalView : MvpView {

    fun addList(list: MutableList<ItemColor>)

    fun showLoadDB()

    fun showColorList()
}