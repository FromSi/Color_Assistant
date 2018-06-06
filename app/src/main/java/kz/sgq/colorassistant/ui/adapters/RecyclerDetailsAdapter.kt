package kz.sgq.colorassistant.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.holders.DetailsHolder
import kz.sgq.colorassistant.ui.util.ItemDetails

class RecyclerDetailsAdapter : RecyclerView.Adapter<DetailsHolder>() {
    private var list: MutableList<ItemDetails> = arrayListOf()

    fun addList(list: MutableList<ItemDetails>){
        this.list = list
        Log.d("TAG_STICK", this.list.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder = DetailsHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_details, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {
        holder.init(list[position])
        holder.setPercent(position)
    }

}