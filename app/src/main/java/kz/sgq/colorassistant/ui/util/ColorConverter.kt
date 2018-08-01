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
import java.text.DecimalFormat
import kotlin.math.max

object ColorConverter {
    fun getHex(color: Int) = "#${Integer.toHexString(color)
            .substring(2)}"

    fun getRGB(color: Int) = "${Color.red(color)}  " +
            "${Color.green(color)}  ${Color.blue(color)}"

    fun getHSV(color: Int, index: Int): String {
        val a = FloatArray(3)
        Color.colorToHSV(color, a)
        val h = (a[0].toInt()).toString()
        val s = DecimalFormat("0.#").format(a[1].toDouble() * 100).toString()
        val v = DecimalFormat("0.#").format(a[2].toDouble() * 100).toString()

        return when (index) {
            0 -> "$h°"
            1 -> "$s%"
            2 -> "$v%"
            else -> "error"
        }
    }

    fun getHSV(color: Int): String {
        return "${getHSV(color, 0)}  ${getHSV(color, 1)}  ${getHSV(color, 2)}"
    }

    fun getCMYK(color: Int, index: Int): String {
        val red = Color.red(color) / 255.0
        val green = Color.green(color) / 255.0
        val blue = Color.blue(color) / 255.0
        val k = 1 - max(red, max(green, blue))
        val c = (((1 - red - k) / (1 - k)) * 100).toInt()
        val m = (((1 - green - k) / (1 - k)) * 100).toInt()
        val y = (((1 - blue - k) / (1 - k)) * 100).toInt()

        return when (index) {
            0 -> "$c%"
            1 -> "$m%"
            2 -> "$y%"
            3 -> "${(k * 100).toInt()}%"
            else -> "error"
        }
    }

    fun getCMYK(color: Int): String = "${getCMYK(color, 0)} " +
            " ${getCMYK(color, 1)} " +
            " ${getCMYK(color, 2)} " +
            " ${getCMYK(color, 3)}"


    fun getFullAnswer(color: Int) = "${getHex(color)}\n ${getRGB(color)}\n " +
            "${getHSV(color)}\n ${getCMYK(color)}"
}