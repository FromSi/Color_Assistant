package kz.sgq.colorassistant.ui.fragment.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_info_color.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorConverter


class InfoColorBottomSheet : BottomSheetDialogFragment() {
    private var color: Int = 0

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        val view = View.inflate(context, R.layout.bottom_sheet_info_color, null)

        dialog!!.setContentView(view)

        val bottomSheet = BottomSheetBehavior.from(view.parent as View)

        if (bottomSheet != null) {
            bottomSheet.peekHeight = resources
                    .getDimension(R.dimen.bottom_sheet_info_color)
                    .toInt()

            initView(view)
            bottomSheet.setBottomSheetCallback(initCallback())
            view.requestLayout()
        }
    }

    fun setColor(color: Int) {
        this.color = color
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

    private fun initCallback(): BottomSheetBehavior.BottomSheetCallback =
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {

                }

                override fun onStateChanged(p0: View, p1: Int) {

                    if (p1 == BottomSheetBehavior.STATE_HIDDEN)
                        dismiss()
                }
            }
}