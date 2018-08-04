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
import android.util.Log
import io.reactivex.functions.Consumer
import kz.sgq.colorassistant.mvp.model.interfaces.ImageModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.util.interfaces.OnEventItemListener
import kz.sgq.colorassistant.ui.util.java.MMCQ

class ImageModelImpl : ImageModel {
    private var result: MutableList<IntArray> = arrayListOf()
    private var cloudList: MutableList<Cloud> = arrayListOf()

    override fun setCurrentImage(currentImage: Bitmap) {
        val changeBitmap = Bitmap.createScaledBitmap(
                currentImage,
                (currentImage.width * 0.3f).toInt(),
                (currentImage.height * 0.3f).toInt(),
                false
        )
        result = MMCQ.compute(changeBitmap, 5)
        initCloud()
    }

    override fun getCurrentImage(): MutableList<IntArray> = result

    override fun calcAverageColor(): MutableList<Int> {
        val list: MutableList<Int> = arrayListOf()
        val count = 0

        for (i in 3 until result.size){
            var r = 0
            var g = 0
            var b = 0

            for (j in count until i){
                r += result[j][0]
                g += result[j][1]
                b += result[j][2]
            }

            list.add(Color.rgb(255 - (r / (i - count)), 255 - (g / (i - count)), 255 - (b / (i - count))))
        }

        return list
    }

    override fun saveCloud(index: Int) {
        DataBaseRequest.insertCloud(cloudList[index], object : OnEventItemListener{
            override fun success() {
                DataBaseRequest.getColor()
                        ?.subscribe {
                            cloudList[index] = it
                        }
            }

            override fun error() {

            }

        })
    }

    override fun deleteCloud(index: Int) {
        DataBaseRequest.deleteCloud(cloudList[index])
    }

    private fun initCloud(){
        for (i in 0..2){
            val cloud = Cloud(
                    ColorConverter.getHex(Color.rgb(result[0][0], result[0][1], result[0][2])),
                    ColorConverter.getHex(Color.rgb(result[1][0], result[1][1], result[1][2])),
                    ColorConverter.getHex(Color.rgb(result[2][0], result[2][1], result[2][2]))
            )

            if (i >= 1)
                cloud.colFour = ColorConverter.getHex(Color.rgb(result[3][0], result[3][1], result[3][2]))

            if (i >= 2)
                cloud.colFive = ColorConverter.getHex(Color.rgb(result[4][0], result[4][1], result[4][2]))

            cloudList.add(cloud)
        }
    }
}