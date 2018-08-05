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
import kz.sgq.colorassistant.ui.util.interfaces.OnDeleteItemListener

class DeleteDialog : DialogFragment() {
    private lateinit var deleteListener: OnDeleteItemListener
    private var index: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_color_delete_title)
        val message = resources.getString(R.string.dialog_color_delete_message)
        val positive = resources.getString(R.string.dialog_color_delete_positive)
        val neutral = resources.getString(R.string.dialog_color_delete_neutral)
        val dialog = AlertDialog.Builder(activity!!)

        dialog.setTitle(title)
        dialog.setMessage(message)

        dialog.setPositiveButton(positive) { _, _ -> deleteListener.onDelete(index) }

        dialog.setNeutralButton(neutral) { _, _ -> }

        return dialog.create()
    }

    fun clickListener(index: Int, deleteListener: OnDeleteItemListener) {
        this.index = index
        this.deleteListener = deleteListener
    }
}