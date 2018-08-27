package kz.sgq.colorassistant.mvp.model.fragment.interfaces

import kz.sgq.colorassistant.mvp.model.fragment.GlobalModelImpl

interface GlobalModel {

    fun setLike(id: Int, like: Boolean)

    fun setReadListener(readListener: GlobalModelImpl.OnReadListener)

    fun handlerColorListener(itemCount: Int, lastVisibleItem: Int)

    fun initData()
}