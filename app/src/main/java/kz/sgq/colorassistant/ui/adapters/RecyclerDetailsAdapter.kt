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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_hsl.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.holders.DetailsHolder
import kz.sgq.colorassistant.ui.util.ItemDetails

class RecyclerDetailsAdapter : RecyclerView.Adapter<DetailsHolder>() {
    private var list: MutableList<ItemDetails> = arrayListOf()
    private var index: Int = -1

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): DetailsHolder = DetailsHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_hsl, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: DetailsHolder, p1: Int) {

        if (index >= 0 && index == p1)
            p0.itemView.imgColor.layoutParams.width = p0.itemView
                    .resources.getDimension(R.dimen.imgColor_on).toInt()
        else
            p0.itemView.imgColor.layoutParams.width = p0.itemView
                    .resources.getDimension(R.dimen.imgColor_off).toInt()

        p0.init(list[p1])
        p0.setPercent(p1)

        p0.itemView.item.setOnClickListener(initClick(p1))
    }

    fun addList(list: MutableList<ItemDetails>) {
        this.list = list

        notifyDataSetChanged()
    }

    private fun initClick(p1: Int): View.OnClickListener = View.OnClickListener {
        index = if ((index == -1) || (index != p1)) p1 else -1

        notifyDataSetChanged()
    }
}