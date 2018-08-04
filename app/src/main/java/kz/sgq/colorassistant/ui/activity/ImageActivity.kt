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

package kz.sgq.colorassistant.ui.activity

import android.os.Bundle
import kz.sgq.colorassistant.R
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.net.Uri
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.activity_image.*
import kz.sgq.colorassistant.mvp.presenter.ImagePresenter
import kz.sgq.colorassistant.mvp.view.ImageView
import kz.sgq.colorassistant.ui.view.ItemColor

class ImageActivity : MvpAppCompatActivity(), ImageView {
    @InjectPresenter
    lateinit var presenter: ImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        initColor()
        initClick()

        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.initImage(resultCode, data)
    }

    override fun initItemsColor(list: MutableList<IntArray>) {
        for (i in 0 until list_one.childCount) {
            (list_one.getChildAt(i) as ItemColor).setMoveItem(false)
            (list_one.getChildAt(i) as ItemColor).setColor(Color.rgb(
                    list[i][0],
                    list[i][1],
                    list[i][2])
            )
        }

        for (i in 0 until list_two.childCount) {
            (list_two.getChildAt(i) as ItemColor).setMoveItem(false)
            (list_two.getChildAt(i) as ItemColor).setColor(Color.rgb(
                    list[i][0],
                    list[i][1],
                    list[i][2])
            )
        }

        for (i in 0 until list_three.childCount) {
            (list_three.getChildAt(i) as ItemColor).setMoveItem(false)
            (list_three.getChildAt(i) as ItemColor).setColor(Color.rgb(
                    list[i][0],
                    list[i][1],
                    list[i][2])
            )
        }
    }

    override fun initListBackground(list: MutableList<Int>) {
        list_one.setBackgroundColor(list[0])
        list_two.setBackgroundColor(list[1])
        list_three.setBackgroundColor(list[2])
    }

    override fun initImage(photoUri: Uri) {
        try {
            val currentImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            presenter.setCurrentImage(currentImage)
            image.setImageBitmap(currentImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initColor() {
        val d = resources.getDrawable(R.drawable.save)
        val f: ColorFilter = LightingColorFilter(Color.BLACK, resources.getColor(R.color.like))
        d.colorFilter = f
        save_one.setLikeDrawable(d)
        save_two.setLikeDrawable(d)
        save_three.setLikeDrawable(d)
    }

    private fun initClick() {
        save_one.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                presenter.actionCloud(0, true)
            }

            override fun unLiked(p0: LikeButton?) {
                presenter.actionCloud(0, false)
            }

        })

        save_two.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                presenter.actionCloud(1, true)
            }

            override fun unLiked(p0: LikeButton?) {
                presenter.actionCloud(1, false)
            }

        })

        save_three.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                presenter.actionCloud(2, true)
            }

            override fun unLiked(p0: LikeButton?) {
                presenter.actionCloud(2, false)
            }

        })
    }
}
