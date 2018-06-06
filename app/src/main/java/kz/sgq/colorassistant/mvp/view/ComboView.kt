package kz.sgq.colorassistant.mvp.view

import com.arellomobile.mvp.MvpView

interface ComboView : MvpView {
    fun init(size: Int)

    fun initHeader(i: Int, j: Int, color: Int)

    fun initColor(i: Int, color: Int)

    fun initColors(i: Int, nameTypeColor: String, value: String)

    fun setBackgroundColor(color: Int)

    fun setTextColor(color: Int)
}