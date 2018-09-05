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

import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.f_info.view.*
import kotlinx.android.synthetic.main.item_color_image.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.view.ItemColor
import android.support.v7.app.AppCompatDelegate
import kz.sgq.colorassistant.ui.util.ColorAttrUtil


class ImageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun initColors(colors: MutableList<Int>) {

        itemView.list.removeAllViews()

        for (i in 0 until colors.size) {

            itemView.list.addView(
                    ItemColor(itemView.context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                (itemView.resources.getDimension(R.dimen.image_item_colors_height))
                                        .toInt()
                        )

                        setMoveItem(false)
                        setScroll(true)
                        setColor(colors[i])
                    }
            )
        }
    }

    fun nightMode() {
        val share = itemView.resources.getDrawable(R.drawable.share)
        val save = itemView.resources.getDrawable(R.drawable.save)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            itemView.share.setUnlikeDrawable(share.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
            itemView.save.setLikeDrawable(save.apply {

                colorFilter = LightingColorFilter(Color.GRAY, ColorAttrUtil.getColorAccent(itemView.context))
            })
            itemView.save.setUnlikeDrawable(save.apply {

                colorFilter = LightingColorFilter(Color.GRAY, Color.GRAY)
            })
        } else {

            itemView.share.setUnlikeDrawable(share.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
            itemView.save.setLikeDrawable(save.apply {

                colorFilter = LightingColorFilter(Color.BLACK, ColorAttrUtil.getColorAccent(itemView.context))
            })
            itemView.save.setUnlikeDrawable(save.apply {

                colorFilter = LightingColorFilter(Color.BLACK, Color.BLACK)
            })
        }
    }

    fun initLike(like: Boolean) {
        itemView.save.isLiked = like
    }

    fun initBackground(color: Int) {

        itemView.list.setBackgroundColor(color)
    }

    fun initTextColors(color: Int) {

        itemView.hex.text = ColorConverter.getHex(color)

        for (i in 0 until itemView.rgb.childCount)
            (itemView.rgb.getChildAt(i) as TextView).text = when (i) {
                0 -> Color.red(color).toString()
                1 -> Color.green(color).toString()
                2 -> Color.blue(color).toString()
                else -> "error"
            }

        for (i in 0 until itemView.hsv.childCount)
            (itemView.hsv.getChildAt(i) as TextView).text = ColorConverter.getHSV(color, i)

        for (i in 0 until itemView.cmyk.childCount)
            (itemView.cmyk.getChildAt(i) as TextView).text = ColorConverter.getCMYK(color, i)
    }

    fun initIcons(colors: MutableList<Int>) {

        itemView.icons.removeAllViews()
        itemView.info.visibility = View.GONE

        for (i in 0 until colors.size) {

            addCircleView(i, colors[i])
            addLineView(i != (colors.size - 1))
        }
    }

    fun initMore() {

        itemView.apply {

            more.setOnClickListener {

                //                TransitionManager.beginDelayedTransition()

                if (info.visibility == View.VISIBLE) {
                    more.text = resources.getString(R.string.more)
                    info.visibility = View.GONE
                } else {
                    more.text = resources.getString(R.string.hide)
                    info.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun addLineView(bool: Boolean) {

        if (bool)
            itemView.icons.addView(
                    View(itemView.context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                                (itemView.resources.getDimension(R.dimen.line_image_w))
                                        .toInt(),
                                (itemView.resources.getDimension(R.dimen.line_image_h))
                                        .toInt()
                        )

                        setBackgroundColor(ColorAttrUtil.getColorLine(itemView.context))
                    }
            )
    }

    private fun addCircleView(i: Int, color: Int) {

        itemView.icons.addView(
                CircleImageView(itemView.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                            (itemView.resources.getDimension(R.dimen.item_view_image))
                                    .toInt(),
                            (itemView.resources.getDimension(R.dimen.item_view_image))
                                    .toInt()
                    ).apply {
                        val margin = (itemView.resources.getDimension(
                                R.dimen.item_view_image_margin
                        ).toInt())

                        if (i != 0)
                            setMargins(0, margin, 0, margin)
                        else
                            setMargins(0, 0, 0, margin)
                    }

                    setImageBitmap(
                            Bitmap.createBitmap(
                                    1,
                                    1,
                                    Bitmap.Config.RGB_565
                            ).apply {

                                if (i != 0)
                                    eraseColor(ColorAttrUtil.getColorLine(itemView.context))
                                else
                                    eraseColor(color)
                            }
                    )

                    setOnClickListener {

                        recolorIcon()
                        setImageBitmap(
                                Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)
                                        .apply { eraseColor(color) }
                        )
                        initTextColors(color)
                    }
                }
        )
    }

    private fun recolorIcon() {

        for (i in 0 until itemView.icons.childCount)
            if ((i % 2) == 0)
                (itemView.icons.getChildAt(i) as CircleImageView).setImageBitmap(
                        Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)
                                .apply { eraseColor(ColorAttrUtil.getColorLine(itemView.context)) }
                )
    }
}