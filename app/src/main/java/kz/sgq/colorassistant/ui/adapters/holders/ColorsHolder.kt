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
import kotlinx.android.synthetic.main.item_colors.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.RecyclerColorsAdapter
import kz.sgq.colorassistant.ui.adapters.holders.base.BaseComboCardHolder
import kz.sgq.colorassistant.ui.util.ColorAttrUtil
import kz.sgq.colorassistant.ui.util.ItemColor

class ColorsHolder(itemView: View?) : BaseComboCardHolder(itemView!!) {

    fun setLiked(like: Boolean) {
        itemView.like.isLiked = like
    }

    fun setView(itemColor: ItemColor, clickListener: RecyclerColorsAdapter.OnClickListener) {

        itemView.view.setOnClickListener {
            itemView.view.isLiked = false

            clickListener.onView(itemColor)
        }
    }

    fun nightMode() {
        val l = itemView.resources.getDrawable(R.drawable.like)
        val v = itemView.resources.getDrawable(R.drawable.view)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            itemView.like.setLikeDrawable(l.apply {

                colorFilter = LightingColorFilter(Color.GRAY, ColorAttrUtil.getColorAccent(itemView.context))
            })
            itemView.like.setUnlikeDrawable(l.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
            itemView.view.setUnlikeDrawable(v.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
        } else {

            itemView.like.setLikeDrawable(l.apply {

                colorFilter = LightingColorFilter(Color.BLACK, ColorAttrUtil.getColorAccent(itemView.context))
            })
            itemView.like.setUnlikeDrawable(l.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
            itemView.view.setUnlikeDrawable(v.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
        }
    }
}