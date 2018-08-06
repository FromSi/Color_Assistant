package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView

interface ComboView : MvpView {

    fun initFragment(list: MutableList<String>)

    fun showShare(textShare: String)
}