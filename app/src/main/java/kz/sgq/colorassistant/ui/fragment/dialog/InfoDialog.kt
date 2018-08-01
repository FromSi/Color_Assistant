package kz.sgq.colorassistant.ui.fragment.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import kotlinx.android.synthetic.main.dialog_info.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorConverter


class InfoDialog : DialogFragment() {
    private var color: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_color_info_title)
        val positive = resources.getString(R.string.dialog_color_info_positive)
        val dialog = AlertDialog.Builder(activity!!)
        val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_info, null)

        dialog.setTitle(title)
        dialog.setView(customLayout)

        initView(customLayout)

        dialog.setPositiveButton(positive) { _, _ -> }

        return dialog.create()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(view: View) {
        view.hex_text.text = "Hex ${ColorConverter.getHex(color)}"

        view.r_text.text = "R ${Color.red(color)}"
        view.g_text.text = "G ${Color.green(color)}"
        view.b_text.text = "B ${Color.blue(color)}"

        view.h_text.text = "H ${ColorConverter.getHSV(color, 0)}"
        view.s_text.text = "S ${ColorConverter.getHSV(color, 1)}"
        view.v_text.text = "V ${ColorConverter.getHSV(color, 2)}"

        view.c_text.text = "C ${ColorConverter.getCMYK(color, 0)}"
        view.m_text.text = "M ${ColorConverter.getCMYK(color, 1)}"
        view.y_text.text = "Y ${ColorConverter.getCMYK(color, 2)}"
        view.k_text.text = "K ${ColorConverter.getCMYK(color, 3)}"
    }

    fun setColor(color: Int) {
        this.color = color
    }
}