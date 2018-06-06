package kz.sgq.colorassistant.ui.util

import android.graphics.Color
import java.text.DecimalFormat
import kotlin.math.max

object ColorConverter {
    fun getHex(color: Int) = "#${Integer.toHexString(color)
            .substring(2)}"

    fun getRGB(color: Int) = "${Color.red(color)}  " +
            "${Color.green(color)}  ${Color.blue(color)}"


    fun getHSV(color: Int): String {
        val a = FloatArray(3)
        Color.colorToHSV(color, a)
        val h = (a[0].toInt()).toString()
        val s = DecimalFormat("0.#").format(a[1].toDouble() * 100).toString()
        val v = DecimalFormat("0.#").format(a[2].toDouble() * 100).toString()
        return "$h°  $s%  $v%"
    }

    fun getCMYK(color: Int): String {
        val red = Color.red(color) / 255.0
        val green = Color.green(color) / 255.0
        val blue = Color.blue(color) / 255.0
        val k = 1 - max(red, max(green, blue))
        val c = (((1 - red - k) / (1 - k)) * 100).toInt()
        val m = (((1 - green - k) / (1 - k)) * 100).toInt()
        val y = (((1 - blue - k) / (1 - k)) * 100).toInt()
        return "$c%  $m%  $y%  ${(k * 100).toInt()}%"
    }

    fun getFullAnswer(color: Int) = "${getHex(color)}\n ${getRGB(color)}\n " +
            "${getHSV(color)}\n ${getCMYK(color)}"

}