package kz.sgq.colorassistant.mvp.model.interfaces

interface ComboModel {

    fun getList(): MutableList<String>

    fun getTextShare(): String

    fun initList(list: MutableList<String>)
}