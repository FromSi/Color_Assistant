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

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_combo_list.view.*
import kz.sgq.colorassistant.R

class ComboListBottomSheet : BottomSheetDialogFragment() {
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClickSaturation()

        fun onClickLightness()
    }

    enum class ComboList { SATURATION, LIGHTNESS }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        val view = View.inflate(context, R.layout.bottom_sheet_combo_list, null)

        dialog!!.setContentView(view)

        val bottomSheet = BottomSheetBehavior.from(view.parent as View)

        if (bottomSheet != null) {

            view.saturation.setOnClickListener(initClickListener(ComboList.SATURATION))
            view.lightness.setOnClickListener(initClickListener(ComboList.LIGHTNESS))
            bottomSheet.setBottomSheetCallback(initCallback())
            view.requestLayout()
        }
    }

    fun setClick(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    private fun initClickListener(enum: ComboList): View.OnClickListener = View.OnClickListener {

        when (enum) {
            ComboList.SATURATION -> {

                clickListener.onClickSaturation()
                dismiss()
            }
            ComboList.LIGHTNESS -> {

                clickListener.onClickLightness()
                dismiss()
            }
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