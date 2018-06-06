package kz.sgq.colorassistant.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.constraint.Constraints
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_details.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.holders.DetailsHolder
import kz.sgq.colorassistant.ui.util.ItemDetails

class RecyclerDetailsAdapter : RecyclerView.Adapter<DetailsHolder>() {
    private var list: MutableList<ItemDetails> = arrayListOf()
    var index: Int = -1

    fun addList(list: MutableList<ItemDetails>) {
        this.list = list
        Log.d("TAG_STICK", this.list.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder = DetailsHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_details, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {

        if (index >= 0 && index == position) {
            holder.itemView.imgColor.layoutParams.width = holder.itemView.resources.getDimension(R.dimen.imgColor_on).toInt()
        } else {
            holder.itemView.imgColor.layoutParams.width = holder.itemView.resources.getDimension(R.dimen.imgColor_off).toInt()
        }

        holder.init(list[position])
        holder.setPercent(position)
        holder.itemView.cardView.setOnClickListener({
            index = if (index == -1 || index != position) position else -1
            notifyDataSetChanged()
        })

    }

}