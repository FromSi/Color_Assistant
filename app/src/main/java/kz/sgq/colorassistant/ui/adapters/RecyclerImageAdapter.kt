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

package kz.sgq.colorassistant.ui.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_color_image.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.holders.ImageHolder
import kz.sgq.colorassistant.ui.util.ColorConverter

class RecyclerImageAdapter : RecyclerView.Adapter<ImageHolder>() {
    private var colors: MutableList<MutableList<Int>> = mutableListOf()
    private var background: MutableList<Int> = mutableListOf()
    private var shareList: MutableList<String> = mutableListOf()
    private var likeList: MutableList<Boolean> = mutableListOf()

    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onSave(index: Int)

        fun onDelete(index: Int)

        fun onShare(share: String)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageHolder = createView(p0)
            .apply {

                nightMode()
            }

    override fun getItemCount(): Int = background.size

    override fun onBindViewHolder(p0: ImageHolder, p1: Int) {
        p0.itemView.more.text = p0.itemView.resources.getString(R.string.more)
        p0.initColors(colors[p1])
        p0.initLike(likeList[p1])
        p0.initBackground(background[p1])
        p0.itemView.save.setOnLikeListener(initSave(p1))
        p0.itemView.share.setOnLikeListener(initShare(p1))
        p0.initMore()
        p0.initTextColors(colors[p1][0])
        p0.initIcons(colors[p1])
        initShare(p1)
    }

    fun initColors(colors: MutableList<MutableList<Int>>) {
        this.colors = colors

        calcAverageColor()
        initShare()
        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    private fun createView(parent: ViewGroup): ImageHolder = ImageHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color_image, parent, false)
    )

    private fun initShare(index: Int): OnLikeListener = object : OnLikeListener {
        override fun liked(p0: LikeButton?) {
            p0?.isLiked = false

            clickListener.onShare(shareList[index])
        }

        override fun unLiked(p0: LikeButton?) {

        }
    }

    private fun initSave(index: Int): OnLikeListener = object : OnLikeListener {
        override fun liked(p0: LikeButton?) {
            likeList[index] = true

            clickListener.onSave(index)
        }

        override fun unLiked(p0: LikeButton?) {
            likeList[index] = false

            clickListener.onDelete(index)
        }
    }

    private fun initShare() {
        for (i in 0 until colors.size) {
            val share = StringBuffer()

            for (j in 0 until colors[i].size)
                share.append(ColorConverter.getHex(colors[i][j]))

            shareList.add(share.toString())
        }
    }

    private fun calcAverageColor() {
        for (i in 0 until colors.size) {
            var r = 0
            var g = 0
            var b = 0

            for (j in 0 until colors[i].size) {
                r += Color.red(colors[i][j])
                g += Color.green(colors[i][j])
                b += Color.blue(colors[i][j])
            }

            background.add(Color.rgb(
                    255 - (r / colors[i].size),
                    255 - (g / colors[i].size),
                    255 - (b / colors[i].size)
            ))
            likeList.add(false)
        }
    }
}