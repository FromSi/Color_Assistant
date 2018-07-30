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
import kz.sgq.colorassistant.ui.util.interfaces.OnItemCloudClickListener

class CloudHolder(itemView: View?) : BaseComboCardHolder(itemView!!) {

    fun setView(cloud: Cloud, clickListener: OnItemCloudClickListener) {
        itemView.view.setOnClickListener {
            clickListener.viewClick(cloud)
            itemView.view.isLiked = false
        }
    }

    fun setShare(cloud: Cloud, clickListener: OnItemCloudClickListener) {
        itemView.share.setOnClickListener {
            clickListener.shareClick(cloud)
            itemView.share.isLiked = false
        }
    }

    fun setDelete(cloud: Cloud, index: Int, clickListener: OnItemCloudClickListener) {
        itemView.delete.setOnClickListener {
            clickListener.deleteClick(cloud, index)
            itemView.delete.isLiked = false
        }
    }
}