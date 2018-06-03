package kz.sgq.colorassistant.infraestructure.networking.common

import kz.sgq.colorassistant.infraestructure.networking.gson.ColorsGson
import kz.sgq.colorassistant.room.table.Colors
import java.util.ArrayList

object GsonConverter {
    fun convertColorsList(colorsGsons: MutableList<ColorsGson>): MutableList<Colors> {
        val list = ArrayList<Colors>()
        for (i in colorsGsons) {
            list.add(Colors(Integer.parseInt(i.idCol),
                    i.col1!!, i.col2!!,
                    i.col3!!, i.col4,
                    i.col5, false))
        }
        return list
    }
}