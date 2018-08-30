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
import kotlinx.android.synthetic.main.item_colors_cloud.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.holders.CloudHolder
import kz.sgq.colorassistant.ui.util.ItemColor

class RecyclerCloudAdapter : RecyclerView.Adapter<CloudHolder>() {
    private var list: MutableList<Cloud> = mutableListOf()
    private var visiblyList: MutableList<Boolean> = mutableListOf()

    private lateinit var clickListener: CloudHolder.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudHolder = createView(parent)
            .apply {

                nightMode()
            }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: CloudHolder, p1: Int) {

        p0.setImagesView(calcItemColor(p1))
        p0.setView(list[p1], clickListener)
        p0.setShare(list[p1], clickListener)
        p0.setDelete(list[p1], clickListener)
        p0.onLoadVisibly(visiblyList[p1])
        p0.itemView.items.setOnClickListener(initClick(p0, p1))
    }

    private fun createView(parent: ViewGroup): CloudHolder = CloudHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_colors_cloud, parent, false)
    )

    fun addList(list: MutableList<Cloud>) {
        this.list = list

        for (i in 0 until list.size)
            visiblyList.add(true)

        notifyDataSetChanged()
    }

    fun addItem(cloud: Cloud) {

        list.add(0, cloud)
        visiblyList.add(true)
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int) {

        list.removeAt(index)
        visiblyList.removeAt(index)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: CloudHolder.OnClickListener) {
        this.clickListener = clickListener
    }

    private fun initClick(p0: CloudHolder, p1: Int): View.OnClickListener = View.OnClickListener {

        if (visiblyList[p1]) {
            visiblyList[p1] = false

            p0.onLoadVisibly(View.GONE)
        } else {
            visiblyList[p1] = true

            p0.onLoadVisibly(View.VISIBLE)
        }
    }

    private fun calcItemColor(index: Int): ItemColor {
        val list: MutableList<String> = arrayListOf()

        list.add(this.list[index].colOne)
        list.add(this.list[index].colTwo)
        list.add(this.list[index].colThree)

        if (this.list[index].colFour != null)
            list.add(this.list[index].colFour!!)

        if (this.list[index].colFive != null)
            list.add(this.list[index].colFive!!)

        return ItemColor(0, list, false)
    }
}