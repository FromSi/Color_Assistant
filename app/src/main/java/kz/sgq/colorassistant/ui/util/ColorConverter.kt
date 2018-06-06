package kz.sgq.colorassistant.ui.util

import android.graphics.Color
import java.text.DecimalFormat

object ColorConverter {
    fun getHex(color: Int) = "#${Integer.toHexString(color)
            .substring(2)}"

    fun getRGB(color: Int) = "${Color.red(color)}, " +
            "${Color.green(color)}, ${Color.blue(color)}"


    fun getHSV(color: Int): String {
        val a = FloatArray(3)
        Color.colorToHSV(color, a)
        val h = DecimalFormat("0.##").format(a[0].toDouble()).toString()
        val s = DecimalFormat("0.##").format(a[1].toDouble()).toString()
        val v = DecimalFormat("0.##").format(a[2].toDouble()).toString()
        return "$h, $s, $v"
    }

    fun getFullAnswer(color: Int) = "${getHex(color)}\n ${getRGB(color)}\n ${getHSV(color)}"

}