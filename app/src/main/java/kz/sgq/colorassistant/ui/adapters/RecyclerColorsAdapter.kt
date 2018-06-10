package kz.sgq.colorassistant.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_colors.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.holders.ColorsHolder
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.ItemContainer
import kz.sgq.colorassistant.ui.util.interfaces.OnItemClickListener
import com.like.LikeButton



class RecyclerColorsAdapter : RecyclerView.Adapter<ColorsHolder>() {
    private val itemContainer = ItemContainer()
    private lateinit var clickListener: OnItemClickListener

    fun addItems(itemList: MutableList<ItemColor>){
        itemContainer.addItems(itemList)
        notifyDataSetChanged()
    }

    fun addItem(item: ItemColor){
        itemContainer.addItem(item)
        notifyDataSetChanged()
    }

    fun deleteItem(id: Int){
        itemContainer.deleteItem(id)
        notifyDataSetChanged()
    }

    fun clearItems(){
        itemContainer.clearItems()
        notifyDataSetChanged()
    }

    fun dislike(id: Int){
        itemContainer.dislike(id)
        notifyDataSetChanged()
    }

    fun updateItems(index: Int){
        itemContainer.updateItems(index)
        notifyDataSetChanged()
    }

    fun SetOnItemClickListener(clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsHolder = ColorsHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_colors, parent, false))

    override fun getItemCount(): Int = itemContainer.itemList.size

    override fun onBindViewHolder(holder: ColorsHolder, position: Int) {
        holder.setImagesView(itemContainer.itemList[position])
        holder.setLiked(itemContainer.likeList[position])
        holder.setViewButton(itemContainer.itemList[position], clickListener)
        holder.onLoadVisibly(itemContainer.visiblyList[position])
        holder.itemView.like.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                onLikeClick(position, true, likeButton.rootView)
            }

            override fun unLiked(likeButton: LikeButton) {
                onLikeClick(position, false, likeButton.rootView)
            }
        })
        holder.itemView.items.setOnClickListener({
            if (itemContainer.visiblyList[position]){
                holder.onLoadVisibly(View.GONE)
                itemContainer.visiblyList[position] = false
            } else {
                holder.onLoadVisibly(View.VISIBLE)
                itemContainer.visiblyList[position] = true
            }
        })
    }

    private fun onLikeClick(position: Int, like: Boolean, view: View){
        clickListener.onItemLikeClick(view,
                itemContainer.itemList[position].id, like)
        itemContainer.likeList[position] = like
    }
}