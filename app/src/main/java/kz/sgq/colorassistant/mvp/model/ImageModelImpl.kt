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

package kz.sgq.colorassistant.mvp.model

import android.graphics.Bitmap
import android.graphics.Color
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sgq.colorassistant.mvp.model.interfaces.ImageModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.util.interfaces.OnClickListener
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.java.MMCQ
import java.util.*

class ImageModelImpl : ImageModel {
    private var state = false
    private val count = 10

    private lateinit var colorList: MutableList<MutableList<Int>>
    private lateinit var cloudList: MutableList<Cloud>

    override fun setCurrentImage(currentImage: Bitmap, click: OnClickListener) {
        colorList = arrayListOf()
        cloudList = arrayListOf()

        val maybe: Maybe<MutableList<IntArray>> = Maybe.create {
            val changeBitmap = Bitmap.createScaledBitmap(
                    currentImage,
                    (currentImage.width * 0.3f).toInt(),
                    (currentImage.height * 0.3f).toInt(),
                    false
            )

            it.onSuccess(MMCQ.compute(changeBitmap, count))
        }

        maybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    initColorList(it)
                    click.onClick()
                }
    }

    override fun setState(state: Boolean) {
        this.state = state
    }

    override fun getCloud(index: Int): Cloud = cloudList[index]

    override fun getState(): Boolean = state

    override fun getColorList(): MutableList<MutableList<Int>> = colorList

    override fun saveCloud(index: Int) {

        DataBaseRequest.insertCloud(cloudList[index], object : OnEventItemListener {

            override fun onSuccess() {

                DataBaseRequest.getColor()
                        ?.subscribe { cloudList[index] = it }
            }

            override fun onError() {

            }
        })
    }

    override fun deleteCloud(index: Int) {

        DataBaseRequest.deleteCloud(cloudList[index])
    }

    private fun initColorList(result: MutableList<IntArray>) {
        val r = Random(System.currentTimeMillis())

        for (i in 0 until count) {
            val list: MutableList<Int> = arrayListOf()

            if (i <= 2) {

                for (j in 0 until (i + 3)) {
                    val num = Color.rgb(result[j][0], result[j][1], result[j][2])

                    list.add(num)
                }
            } else {
                val r1 = 3 + r.nextInt(5 - 3 + 1)
                val array = IntArray(r1)
                array[0] = 3 + r.nextInt(count - 3)

                for (j in 1 until r1)
                    array[j] = randomNumber(r1, array, r, count)

                for (j in 0 until r1)
                    list.add(Color.rgb(
                            result[array[j]][0],
                            result[array[j]][1],
                            result[array[j]][2]
                    ))
            }

            cloudList.add(initCloud(list))
            colorList.add(list)
        }
    }

    private fun randomNumber(r1: Int, array: IntArray, r: Random, count: Int): Int {
        val r0 = 3 + r.nextInt(count - 3)

        for (k in 0 until array.size)
            if (array[k] == r0)
                return randomNumber(r1, array, r, count)

        return r0
    }

    private fun initCloud(list: MutableList<Int>): Cloud {
        val cloud = Cloud(
                ColorConverter.getHex(list[0]),
                ColorConverter.getHex(list[1]),
                ColorConverter.getHex(list[2])
        )

        if (list.size >= 4)
            cloud.colFour = ColorConverter.getHex(list[3])

        if (list.size >= 5)
            cloud.colFive = ColorConverter.getHex(list[4])

        return cloud
    }
}