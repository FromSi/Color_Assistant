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
import com.like.LikeButton

class RecyclerColorsAdapter : RecyclerView.Adapter<ColorsHolder>() {
    private val list = ItemContainer()

    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onLike(id: Int, like: Boolean)

        fun onView(itemColor: ItemColor)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ColorsHolder = ColorsHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_colors, parent, false)
    )

    override fun getItemCount(): Int = list.itemList.size

    override fun onBindViewHolder(p0: ColorsHolder, @SuppressLint("RecyclerView") p1: Int) {

        p0.setImagesView(list.itemList[p1])
        p0.setLiked(list.likeList[p1])
        p0.setView(list.itemList[p1], clickListener)
        p0.onLoadVisibly(list.visiblyList[p1])
        p0.itemView.like.setOnLikeListener(initLike(p1))
        p0.itemView.items.setOnClickListener(initClick(p0, p1))
    }

    fun addItemList(list: MutableList<ItemColor>) {

        this.list.addItemList(list)
        notifyDataSetChanged()
    }

    fun setItemList(list: MutableList<ItemColor>) {

        this.list.setList(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    private fun initLike(p1: Int): OnLikeListener = object : OnLikeListener {
        override fun liked(likeButton: LikeButton) {

            onLikeClick(p1, true)
        }

        override fun unLiked(likeButton: LikeButton) {

            onLikeClick(p1, false)
        }
    }

    private fun initClick(
            p0: ColorsHolder,
            p1: Int
    ): View.OnClickListener = View.OnClickListener {

        if (list.visiblyList[p1]) {
            list.visiblyList[p1] = false

            p0.onLoadVisibly(View.GONE)
        } else {
            list.visiblyList[p1] = true

            p0.onLoadVisibly(View.VISIBLE)
        }
    }

    private fun onLikeClick(position: Int, like: Boolean) {

        clickListener.onLike(list.itemList[position].id, like)
        list.likeList[position] = like
    }
}