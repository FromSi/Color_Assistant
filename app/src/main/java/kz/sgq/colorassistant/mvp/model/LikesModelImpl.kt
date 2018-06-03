package kz.sgq.colorassistant.mvp.model

import kz.sgq.colorassistant.mvp.model.interfaces.LikesModel
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

class LikesModelImpl: LikesModel {
    override fun getItemColor(colors: MutableList<Colors>): MutableList<ItemColor> {
        val list = ArrayList<ItemColor>()

        for (i in colors.indices) {
            val stringList = ArrayList<String>()
            stringList.add(colors[i].colOne)
            stringList.add(colors[i].colTwo)
            stringList.add(colors[i].colThree)

            if (colors[i].colFour != null)
                stringList.add(colors[i].colFour!!)

            if (colors[i].colFive != null)
                stringList.add(colors[i].colFive!!)

            list.add(ItemColor(colors[i].idCol,
                    stringList, colors[i].like!!))
        }
        return list
    }
}