package kz.sgq.colorassistant.mvp.model

import kz.sgq.colorassistant.mvp.model.interfaces.ComboModel

class ComboModelImpl : ComboModel {
    private lateinit var list: MutableList<String>
    private lateinit var textShare: String

    override fun initList(list: MutableList<String>) {
        this.list = list
        val text = StringBuffer("${list[0]}${list[1]}${list[2]}")

        if (list.size >= 4)
            text.append(list[3])

        if (list.size >= 5)
            text.append(list[4])

        textShare = text.toString()
    }

    override fun getList(): MutableList<String> = list

    override fun getTextShare(): String = textShare
}