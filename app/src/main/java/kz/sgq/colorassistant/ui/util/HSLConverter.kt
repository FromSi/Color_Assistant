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

package kz.sgq.colorassistant.ui.util

import android.graphics.Color
import java.util.ArrayList

object HSLConverter {
    private const val MAX_NUMBER = 21

    fun getSaturationList(color: Int): MutableList<Int> {
        val list = ArrayList<Int>()
        val rgb = intArrayOf(Color.red(color),
                Color.green(color),
                Color.blue(color))
        val arf = (Math.max(rgb[0],
                Math.max(rgb[1], rgb[2])) + Math.min(rgb[0],
                Math.min(rgb[1], rgb[2]))) / 2
        val r = getCalcSaturationRGB(rgb[0], arf)
        val g = getCalcSaturationRGB(rgb[1], arf)
        val b = getCalcSaturationRGB(rgb[2], arf)

        for (i in 0 until MAX_NUMBER)
            list.add(Color.rgb(r[i], g[i], b[i]))

        return list
    }

    fun getSaturation(color: Int, float: Float): Int {
        val rgb = intArrayOf(Color.red(color),
                Color.green(color),
                Color.blue(color))
        val arf = (Math.max(rgb[0],
                Math.max(rgb[1], rgb[2])) + Math.min(rgb[0],
                Math.min(rgb[1], rgb[2]))) / 2
        val r = calcSaturation(rgb[0], arf, float)
        val g = calcSaturation(rgb[1], arf, float)
        val b = calcSaturation(rgb[2], arf, float)

        return Color.rgb(r, g, b)
    }

    fun getLightnessList(color: Int): MutableList<Int> {
        val list = ArrayList<Int>()
        val r = getCalcLightnessRGB(Color.red(color))
        val g = getCalcLightnessRGB(Color.green(color))
        val b = getCalcLightnessRGB(Color.blue(color))

        for (i in 0 until MAX_NUMBER)
            list.add(Color.rgb(r[i], g[i], b[i]))

        return list
    }

    private fun calcSaturation(
            rgbColor: Int,
            arf: Int,
            float: Float
    ): Int = if (rgbColor <= 128 && rgbColor != arf) (arf - (arf - rgbColor) * (float)).toInt()
    else if (rgbColor > 128 && rgbColor != arf) (arf + (rgbColor - arf) * (float)).toInt()
    else arf

    private fun getCalcLightnessRGB(rgbColor: Int): MutableList<Int> {
        var list: MutableList<Int> = arrayListOf()

        for (i in 0 until MAX_NUMBER - 10)
            if (rgbColor < 255) {
                val num = rgbColor + (255 - rgbColor) * (i * 0.1f)

                list.add(num.toInt())
            } else
                list.add(255)

        list = rewrite(list)

        for (i in 1 until MAX_NUMBER - 9)
            if (rgbColor > 0) {
                val num = rgbColor - rgbColor * (i * 0.1f)

                list.add(num.toInt())
            } else
                list.add(0)

        return list
    }

    private fun getCalcSaturationRGB(rgbColor: Int, arf: Int): MutableList<Int> {
        val list: MutableList<Int> = arrayListOf()

        for (i in 0 until MAX_NUMBER)
            if (rgbColor <= 128 && rgbColor != arf) {
                val num = arf - (arf - rgbColor) * (i * 0.05f)

                list.add(num.toInt())
            } else if (rgbColor > 128 && rgbColor != arf) {
                val num = arf + (rgbColor - arf) * (i * 0.05f)

                list.add(num.toInt())
            } else
                list.add(arf)

        return rewrite(list)
    }

    private fun rewrite(list: MutableList<Int>): MutableList<Int> {
        val rewriteList: MutableList<Int> = arrayListOf()

        for (i in list.indices)
            rewriteList.add(list[list.size - 1 - i])

        return rewriteList
    }
}