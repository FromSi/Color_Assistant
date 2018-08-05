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

import android.view.View
import kotlinx.android.synthetic.main.item_colors.view.*
import kz.sgq.colorassistant.ui.adapters.holders.base.BaseComboCardHolder
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.interfaces.OnItemColorClickListener

class ColorsHolder(itemView: View?) : BaseComboCardHolder(itemView!!) {

    fun setLiked(like: Boolean) {
        itemView.like.isLiked = like
    }

    fun setView(itemColor: ItemColor, clickListener: OnItemColorClickListener) {
        itemView.view.setOnClickListener {
            clickListener.onView(itemView.view.rootView, itemColor)
            itemView.view.isLiked = false
        }
    }
}