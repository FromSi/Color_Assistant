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
        val massege = resources.getString(R.string.dialog_color_delete_message)
        val positive = resources.getString(R.string.dialog_color_delete_positive)
        val neutral = resources.getString(R.string.dialog_color_delete_neutral)
        val dialog = AlertDialog.Builder(activity!!)

        dialog.setTitle(title)
        dialog.setMessage(massege)

        dialog.setPositiveButton(positive) { _, _ -> deleteListener.delete(index) }

        dialog.setNeutralButton(neutral) { _, _ -> }

        return dialog.create()
    }

    fun clickListener(index: Int, deleteListener: OnDeleteItemListener) {
        this.index = index
        this.deleteListener = deleteListener
    }
}