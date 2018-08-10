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
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import kotlinx.android.synthetic.main.dialog_share.view.*
import kz.sgq.colorassistant.R
import net.glxn.qrgen.android.QRCode

class ShareDialog : DialogFragment() {
    private var text: String = "Error"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_share_title)
        val positive = resources.getString(R.string.dialog_share_positive)
        val neutral = resources.getString(R.string.dialog_share_neutral)
        val dialog = AlertDialog.Builder(activity!!)
        val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_share, null)

        dialog.setTitle(title)
        dialog.setView(customLayout)
        initView(customLayout)

        dialog.setPositiveButton(positive) { _, _ -> }

        dialog.setNeutralButton(neutral) { _, _ -> }

        return dialog.create()
    }

    fun setText(text: String) {
        this.text = text
    }

    private fun initView(view: View) {
        val size = resources.getDimension(R.dimen.dialog_share_size)

        view.qr.setImageBitmap(QRCode.from(text).withSize(size.toInt(), size.toInt()).bitmap())
    }
}