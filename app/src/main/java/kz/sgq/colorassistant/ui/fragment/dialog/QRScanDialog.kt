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

package kz.sgq.colorassistant.ui.fragment.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog_qr.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.view.ItemColor

class QRScanDialog : DialogFragment() {
    private var index: Int = 0
    private var textType: Array<String> = arrayOf("Hex", "RGB", "HSV", "CMYK")

    private lateinit var cloud: Cloud
    private lateinit var clickListener: ItemColor.OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_qr_scan_title)
        val positive = resources.getString(R.string.dialog_qr_scan_positive)
        val neutral = resources.getString(R.string.dialog_qr_scan_neutral)

        return AlertDialog.Builder(activity!!).apply {
            val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_qr, null)

            setTitle(title)
            setView(customLayout)
            initView(customLayout)
            textClick(customLayout)
            setPositiveButton(positive) { _, _ -> clickListener.onClick() }
            setNeutralButton(neutral) { _, _ -> }
        }.create()
    }

    fun parseColor(view: View, color: Int) {
        textType[0] = "Hex\n${ColorConverter.getHex(color)}"
        textType[1] = "RGB\n${ColorConverter.getRGB(color)}"
        textType[2] = "HSV\n${ColorConverter.getHSV(color)}"
        textType[3] = "CMYK\n${ColorConverter.getCMYK(color)}"
        view.info.text = textType[index]
    }

    fun clickListener(clickListener: ItemColor.OnClickListener) {
        this.clickListener = clickListener
    }

    fun cloud(cloud: Cloud) {
        this.cloud = cloud
    }

    private fun initView(view: View) {

        view.item_list.apply {
            addView(createItem(view, Color.parseColor(cloud.colOne)))
            (getChildAt(0) as ItemColor).setEnable(true)
            parseColor(view, Color.parseColor(cloud.colOne))
            addView(createItem(view, Color.parseColor(cloud.colTwo)))
            addView(createItem(view, Color.parseColor(cloud.colThree)))

            if (cloud.colFour != null)
                addView(createItem(view, Color.parseColor(cloud.colFour)))

            if (cloud.colFive != null)
                addView(createItem(view, Color.parseColor(cloud.colFive)))
        }
    }

    private fun textClick(view: View) {

        view.info.apply {
            text = textType[index]

            setOnClickListener {
                index++

                if (index <= 3)
                    view.info.text = textType[index]
                else {
                    index = 0
                    view.info.text = textType[index]
                }
            }
        }
    }

    private fun enableItems(view: View, itemColor: ItemColor) {
        for (i in 0 until view.item_list.childCount)
            (view.item_list.getChildAt(i) as ItemColor).setEnable(false)

        itemColor.setEnable(true)
    }

    private fun createItem(view: View, color: Int): View = ItemColor(context)
            .apply {

                setColor(color)
                setMoveItem(false)
                setOnClickItemColorListener(initClick(view, color, this))
                layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }


    private fun initClick(
            view: View,
            color: Int,
            itemColor: ItemColor
    ): ItemColor.OnClickListener = object : ItemColor.OnClickListener {
        override fun onClick() {

            enableItems(view, itemColor)
            parseColor(view, color)
        }
    }
}