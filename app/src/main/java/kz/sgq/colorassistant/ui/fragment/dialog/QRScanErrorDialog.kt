package kz.sgq.colorassistant.ui.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.interfaces.OnClickListener

class QRScanErrorDialog : DialogFragment() {
    private lateinit var clickListener: OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_qr_scan_error_title)
        val message = resources.getString(R.string.dialog_qr_scan_error_message)
        val positive = resources.getString(R.string.dialog_qr_scan_error_positive)
        val neutral = resources.getString(R.string.dialog_qr_scan_error_neutral)
        val dialog = AlertDialog.Builder(activity!!)

        dialog.setTitle(title)
        dialog.setMessage(message)

        dialog.setPositiveButton(positive) { _, _ -> clickListener.onClick() }

        dialog.setNeutralButton(neutral) { _, _ -> }

        return dialog.create()
    }

    fun clickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }
}