package kz.sgq.colorassistant.ui.adapters.holders

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.item_details.view.*
import kz.sgq.colorassistant.ui.util.ItemDetails

class DetailsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun init(itemDetails: ItemDetails) {
        Log.d("TAG_TEST_COLOR", itemDetails.color.toString())
        itemView.imgLine.setBackgroundColor(itemDetails.color)
        itemView.imgColor.setBackgroundColor(itemDetails.color)
        itemView.nameTypeColor.text = "Hex\nRGB\nHSV\nCMYK"
        itemView.value.text = itemDetails.value
    }

    @SuppressLint("SetTextI18n")
    fun setPercent(position: Int) {
        itemView.percent.text = "${100 - position * 5} ${37.toChar()}"
    }
}