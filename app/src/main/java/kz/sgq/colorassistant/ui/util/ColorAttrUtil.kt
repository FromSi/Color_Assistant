package kz.sgq.colorassistant.ui.util

import android.content.Context
import android.util.TypedValue
import kz.sgq.colorassistant.R

object ColorAttrUtil {

    fun getColorPrimary(context: Context): Int = getColor(context, R.attr.colorPrimary)

    fun getColorPrimaryDark(context: Context): Int = getColor(context, R.attr.colorPrimaryDark)

    fun getColorAccent(context: Context): Int = getColor(context, R.attr.colorAccent)

    fun getColorNightSheet(context: Context): Int = getColor(context, R.attr.colorNightSheet)

    fun getColorNightPopupMenu(context: Context): Int = getColor(context, R.attr.colorNightPopupMenu)

    private fun getColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()

        context.theme.resolveAttribute(attr, typedValue, true)

        return typedValue.data
    }
}