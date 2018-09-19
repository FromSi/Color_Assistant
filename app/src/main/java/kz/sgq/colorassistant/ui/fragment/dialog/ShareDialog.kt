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
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import android.view.View
import kotlinx.android.synthetic.main.dialog_share.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorAttrUtil
import kz.sgq.colorassistant.ui.util.java.PreferencesUtil
import net.glxn.qrgen.android.QRCode

class ShareDialog : DialogFragment() {
    private var text: String = "Error"

    private lateinit var onClickListener: OnClickListener

    interface OnClickListener {
        fun onClickPositive(link: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_share_title)
        val positive = resources.getString(R.string.dialog_share_positive)
        val neutral = resources.getString(R.string.dialog_share_neutral)

        return AlertDialog.Builder(activity!!, PreferencesUtil.getThemeDialogId(context)).apply {
            val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_share, null)

            setTitle(title)
            setView(customLayout)
            initView(customLayout)
            setPositiveButton(positive) { _, _ -> onClickListener.onClickPositive(handlerLink()) }
            setNeutralButton(neutral) { _, _ -> }
        }.create()
    }

    fun setText(text: String) {
        this.text = text
    }

    fun setClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    private fun handlerLink(): String {
        var link: String = "https://colorsapp-sgq.herokuapp.com/link?" +
                "one=${text.substring(0, 7)}&two=${text.substring(7, 14)}&three=${text.substring(14, 21)}"

        if (text.length >= 28) {
            link += "&four=${text.substring(21, 28)}"

            if (text.length >= 35)
                link += "&five=${text.substring(28, 35)}"
        }

        return link
    }

    private fun initView(view: View) {
        val size = resources.getDimension(R.dimen.dialog_share_size)

        view.qr.setImageBitmap(
                QRCode
                        .from(text)
                        .withSize(size.toInt(), size.toInt())
                        .withColor(
                                ColorAttrUtil.getColorQR(view.context),
                                Color.argb(0, 0, 0, 0)
                        )
                        .bitmap()
        )
    }
}