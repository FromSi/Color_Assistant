package kz.sgq.colorassistant.ui.util

import android.graphics.Color
import android.util.Log
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

        for (i in 0 until MAX_NUMBER) {
            list.add(Color.rgb(r[i], g[i], b[i]))
        }

        return list
    }

    fun getSaturation(color: Int, float: Float): Int {
        val rgb = intArrayOf(Color.red(color),
                Color.green(color),
                Color.blue(color))
        val arf = (Math.max(rgb[0],
                Math.max(rgb[1], rgb[2])) + Math.min(rgb[0],
                Math.min(rgb[1], rgb[2]))) / 2

        Log.d("TAG_getSaturation", "$float")
        Log.d("TAG_getSaturation_color", "$color")

        val r = calcSaturation(rgb[0], arf,float)
        val g = calcSaturation(rgb[1], arf, float)
        val b = calcSaturation(rgb[2], arf, float)

        return Color.rgb(r, g, b)
    }

    private fun calcSaturation(
            rgbColor: Int,
            arf: Int,
            float: Float
    ): Int = if (rgbColor <= 128 && rgbColor != arf) (arf - (arf - rgbColor) * (float)).toInt()
    else if (rgbColor > 128 && rgbColor != arf) (arf + (rgbColor - arf) * (float)).toInt()
    else arf


    fun getLightnessList(color: Int): MutableList<Int> {
        val list = ArrayList<Int>()
        val r = getCalcLightnessRGB(Color.red(color))
        val g = getCalcLightnessRGB(Color.green(color))
        val b = getCalcLightnessRGB(Color.blue(color))

        for (i in 0 until MAX_NUMBER) {
            list.add(Color.rgb(r[i], g[i], b[i]))
        }

        return list
    }

    private fun getCalcLightnessRGB(rgbColor: Int): MutableList<Int> {
        var list: MutableList<Int> = arrayListOf()

        for (i in 0 until MAX_NUMBER - 10) {
            if (rgbColor < 255) {
                val num = rgbColor + (255 - rgbColor) * (i * 0.1f)
                list.add(num.toInt())
            } else {
                list.add(255)
            }
        }

        list = rewrite(list)

        for (i in 1 until MAX_NUMBER - 9) {
            if (rgbColor > 0) {
                val num = rgbColor - rgbColor * (i * 0.1f)
                list.add(num.toInt())
            } else {
                list.add(0)
            }
        }

        return list
    }

    private fun getCalcSaturationRGB(rgbColor: Int, arf: Int): MutableList<Int> {
        val list: MutableList<Int> = arrayListOf()

        for (i in 0 until MAX_NUMBER) {
            if (rgbColor <= 128 && rgbColor != arf) {
                val num = arf - (arf - rgbColor) * (i * 0.05f)
                list.add(num.toInt())
            } else if (rgbColor > 128 && rgbColor != arf) {
                val num = arf + (rgbColor - arf) * (i * 0.05f)
                list.add(num.toInt())
            } else {
                list.add(arf)
            }
        }

        return rewrite(list)
    }

    private fun rewrite(list: MutableList<Int>): MutableList<Int> {
        val rewriteList: MutableList<Int> = arrayListOf()
        for (i in list.indices) {
            rewriteList.add(list[list.size - 1 - i])
        }
        return rewriteList
    }
}