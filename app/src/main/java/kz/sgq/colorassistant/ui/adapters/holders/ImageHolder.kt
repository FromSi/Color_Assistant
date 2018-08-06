/*
 * CCopyright 2018 Vlad Weber-Pflaumer
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
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_color_image.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.view.ItemColor

class ImageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun initColors(colors: MutableList<Int>) {

        itemView.list.removeAllViews()

        for (i in 0 until colors.size) {
            val itemColor = ItemColor(itemView.context)

            itemColor.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (itemView.resources.getDimension(R.dimen.image_item_colors_height)).toInt()
            )

            itemColor.setMoveItem(false)
            itemColor.setScroll(true)
            itemColor.setColor(colors[i])
            itemView.list.addView(itemColor)
        }
    }

    fun initSaveColor() {
        val d = itemView.resources.getDrawable(R.drawable.save)
        val f: ColorFilter = LightingColorFilter(
                Color.BLACK,
                itemView.resources.getColor(R.color.like)
        )
        d.colorFilter = f

        itemView.save.setLikeDrawable(d)
    }

    fun initLike(like: Boolean) {
        itemView.save.isLiked = like
    }

    fun initBackground(color: Int) {

        itemView.list.setBackgroundColor(color)
    }
}