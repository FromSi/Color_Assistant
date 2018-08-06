/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.ui.adapters

import android.annotation.SuppressLint
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
import kz.sgq.colorassistant.ui.util.interfaces.OnItemColorClickListener
import com.like.LikeButton

class RecyclerColorsAdapter : RecyclerView.Adapter<ColorsHolder>() {
    private val itemContainer = ItemContainer()
    private lateinit var clickListener: OnItemColorClickListener

    fun addItems(itemList: MutableList<ItemColor>) {

        itemContainer.addItems(itemList)
        notifyDataSetChanged()
    }

    fun addItem(item: ItemColor) {

        itemContainer.addItem(item)
        notifyDataSetChanged()
    }

    fun deleteItem(id: Int) {

        itemContainer.deleteItem(id)
        notifyDataSetChanged()
    }

    fun clearItems() {

        itemContainer.clearItems()
        notifyDataSetChanged()
    }

    fun dislike(id: Int) {

        itemContainer.dislike(id)
        notifyDataSetChanged()
    }

    fun updateItems(index: Int) {

        itemContainer.updateItems(index)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: OnItemColorClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ColorsHolder = ColorsHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_colors, parent, false)
    )

    override fun getItemCount(): Int = itemContainer.itemList.size

    override fun onBindViewHolder(p0: ColorsHolder, @SuppressLint("RecyclerView") p1: Int) {

        p0.setImagesView(itemContainer.itemList[p1])
        p0.setLiked(itemContainer.likeList[p1])
        p0.setView(itemContainer.itemList[p1], clickListener)
        p0.onLoadVisibly(itemContainer.visiblyList[p1])
        p0.itemView.like.setOnLikeListener(initLike(p1))
        p0.itemView.items.setOnClickListener(initClick(p0, p1))
    }

    private fun initLike(p1: Int): OnLikeListener = object : OnLikeListener {

        override fun liked(likeButton: LikeButton) {

            onLikeClick(p1, true, likeButton.rootView)
        }

        override fun unLiked(likeButton: LikeButton) {

            onLikeClick(p1, false, likeButton.rootView)
        }
    }

    private fun initClick(
            p0: ColorsHolder,
            p1: Int
    ): View.OnClickListener = View.OnClickListener {

        if (itemContainer.visiblyList[p1]) {
            itemContainer.visiblyList[p1] = false

            p0.onLoadVisibly(View.GONE)
        } else {
            itemContainer.visiblyList[p1] = true

            p0.onLoadVisibly(View.VISIBLE)
        }
    }

    private fun onLikeClick(position: Int, like: Boolean, view: View) {

        clickListener.onLike(view, itemContainer.itemList[position].id, like)
        itemContainer.likeList[position] = like
    }
}