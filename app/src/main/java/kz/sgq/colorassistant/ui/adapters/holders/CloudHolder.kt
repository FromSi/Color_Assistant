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

import android.graphics.Color
import android.graphics.LightingColorFilter
import android.support.v7.app.AppCompatDelegate
import android.view.View
import kotlinx.android.synthetic.main.item_colors_cloud.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.holders.base.BaseComboCardHolder
import kz.sgq.colorassistant.ui.util.ColorAttrUtil

class CloudHolder(itemView: View?) : BaseComboCardHolder(itemView!!) {

    interface OnClickListener {

        fun onView(cloud: Cloud)

        fun onShare(cloud: Cloud)

        fun onDelete(cloud: Cloud)
    }

    fun setView(cloud: Cloud, clickListener: OnClickListener) {

        itemView.view.setOnClickListener {
            itemView.view.isLiked = false

            clickListener.onView(cloud)
        }
    }

    fun nightMode() {
        val v = itemView.resources.getDrawable(R.drawable.view)
        val s = itemView.resources.getDrawable(R.drawable.share)
        val d = itemView.resources.getDrawable(R.drawable.delete)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            itemView.view.setUnlikeDrawable(v.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
            itemView.share.setUnlikeDrawable(s.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
            itemView.delete.setUnlikeDrawable(d.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
        } else {

            itemView.view.setUnlikeDrawable(v.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
            itemView.share.setUnlikeDrawable(s.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
            itemView.delete.setUnlikeDrawable(d.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
        }
    }

    fun setShare(cloud: Cloud, clickListener: OnClickListener) {

        itemView.share.setOnClickListener {
            itemView.share.isLiked = false

            clickListener.onShare(cloud)
        }
    }

    fun setDelete(cloud: Cloud, clickListener: OnClickListener) {

        itemView.delete.setOnClickListener {
            itemView.delete.isLiked = false

            clickListener.onDelete(cloud)
        }
    }
}