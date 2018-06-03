package kz.sgq.colorassistant.mvp.model.interfaces

import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

interface LikesModel {
    fun getItemColor(colors: MutableList<Colors>): MutableList<ItemColor>
}