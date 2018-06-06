package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView
import kz.sgq.colorassistant.ui.util.ItemDetails

interface InfoView : MvpView {
    fun createSaturation(color: Int, itemList: MutableList<ItemDetails>)
    fun createLightness(color: Int, itemList: MutableList<ItemDetails>)
    fun installViewPager()
}