package kz.sgq.colorassistant.ui.util.interfaces

import android.view.View
import kz.sgq.colorassistant.ui.util.ItemColor

interface OnItemClickListener {
    fun onItemLikeClick(view: View, id: Int, like: Boolean)
    fun onItemViewClick(view: View, itemColor: ItemColor)
}