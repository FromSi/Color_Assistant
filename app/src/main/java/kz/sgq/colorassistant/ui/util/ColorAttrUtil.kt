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