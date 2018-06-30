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

package kz.sgq.colorassistant.ui.adapters.holders

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_details.view.*
import kz.sgq.colorassistant.ui.util.ItemDetails

class DetailsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun init(itemDetails: ItemDetails) {
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