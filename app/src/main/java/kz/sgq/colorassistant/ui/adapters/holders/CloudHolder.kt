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
import kotlinx.android.synthetic.main.item_colors_cloud.view.*
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.holders.base.BaseComboCardHolder

class CloudHolder(itemView: View?) : BaseComboCardHolder(itemView!!) {

    interface OnClickListener {

        fun onView(cloud: Cloud)

        fun onShare(cloud: Cloud)

        fun onDelete(cloud: Cloud, index: Int)
    }

    fun setView(cloud: Cloud, clickListener: OnClickListener) {

        itemView.view.setOnClickListener {
            itemView.view.isLiked = false

            clickListener.onView(cloud)
        }
    }

    fun setShare(cloud: Cloud, clickListener: OnClickListener) {

        itemView.share.setOnClickListener {
            itemView.share.isLiked = false

            clickListener.onShare(cloud)
        }
    }

    fun setDelete(cloud: Cloud, index: Int, clickListener: OnClickListener) {

        itemView.delete.setOnClickListener {
            itemView.delete.isLiked = false

            clickListener.onDelete(cloud, index)
        }
    }
}