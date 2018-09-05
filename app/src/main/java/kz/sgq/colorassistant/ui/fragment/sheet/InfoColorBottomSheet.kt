package kz.sgq.colorassistant.ui.fragment.sheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.app.AppCompatDelegate
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.bottom_sheet_info_color.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorAttrUtil
import kz.sgq.colorassistant.ui.util.ColorConverter


class InfoColorBottomSheet : BottomSheetDialogFragment() {
    private var color: Int = 0

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        View.inflate(context, R.layout.bottom_sheet_info_color, null).apply {

            dialog!!.setContentView(this)

            val bottomSheet = BottomSheetBehavior.from(parent as View)

            if (bottomSheet != null) {
                bottomSheet.peekHeight = resources
                        .getDimension(R.dimen.bottom_sheet_info_color)
                        .toInt()

                initView(this)
                bottomSheet.setBottomSheetCallback(initCallback())
                requestLayout()
            }
        }
    }

    fun setColor(color: Int) {
        this.color = color
    }

    @SuppressLint("SetTextI18n")
    private fun initView(view: View) {
        view.apply {
            hex_text.text = "Hex ${ColorConverter.getHex(color)}"
            r_text.text = "R ${Color.red(color)}"
            g_text.text = "G ${Color.green(color)}"
            b_text.text = "B ${Color.blue(color)}"
            h_text.text = "H ${ColorConverter.getHSV(color, 0)}"
            s_text.text = "S ${ColorConverter.getHSV(color, 1)}"
            v_text.text = "V ${ColorConverter.getHSV(color, 2)}"
            c_text.text = "C ${ColorConverter.getCMYK(color, 0)}"
            m_text.text = "M ${ColorConverter.getCMYK(color, 1)}"
            y_text.text = "Y ${ColorConverter.getCMYK(color, 2)}"
            k_text.text = "K ${ColorConverter.getCMYK(color, 3)}"
        }
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