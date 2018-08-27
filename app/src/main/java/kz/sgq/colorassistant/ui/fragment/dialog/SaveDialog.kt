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
import kz.sgq.colorassistant.R

class SaveDialog : DialogFragment() {
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_save_title)
        val message = resources.getString(R.string.dialog_save_message)
        val positive = resources.getString(R.string.dialog_save_positive)
        val neutral = resources.getString(R.string.dialog_save_neutral)

        return AlertDialog.Builder(activity!!).apply {
            setTitle(title)
            setMessage(message)

            setPositiveButton(positive) { _, _ -> clickListener.onClick() }

            setNeutralButton(neutral) { _, _ -> }
        }.create()
    }

    fun clickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }
}
