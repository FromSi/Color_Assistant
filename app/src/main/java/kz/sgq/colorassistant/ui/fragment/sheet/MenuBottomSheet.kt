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

package kz.sgq.colorassistant.ui.fragment.sheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDelegate
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.bottom_sheet_menu.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.mvp.model.MainModelImpl.MainFragment.*

class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var fragmentCurrent: MainModelImpl.MainFragment
    private lateinit var clickListener: OnClickListener

    interface OnClickListener {

        fun onClick(fragmentCurrent: MainModelImpl.MainFragment)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        View.inflate(context, R.layout.bottom_sheet_menu, null).apply {
            dialog!!.setContentView(this)

            val bottomSheet = BottomSheetBehavior.from(parent as View)

            if (bottomSheet != null) {

                setColor(this)
                global.setOnClickListener(initClickListener(GLOBAL))
                cloud.setOnClickListener(initClickListener(CLOUD))
                bottomSheet.setBottomSheetCallback(initCallback())
                requestLayout()
            }
        }
    }

    fun setFragmentCurrent(fragmentCurrent: MainModelImpl.MainFragment) {
        this.fragmentCurrent = fragmentCurrent
    }

    fun setClick(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    private fun setColor(view: View) {
        val typedValue = TypedValue()

        view.context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)

        when (fragmentCurrent) {

            GLOBAL -> {

                (view.global.getChildAt(0) as ImageView).setColorFilter(typedValue.data)
                (view.global.getChildAt(1) as TextView).setTextColor(typedValue.data)
            }
            CLOUD -> {

                (view.cloud.getChildAt(0) as ImageView).setColorFilter(typedValue.data)
                (view.cloud.getChildAt(1) as TextView).setTextColor(typedValue.data)
            }
            else -> {

            }
        }
    }

    private fun initClickListener(
            fragmentCurrent: MainModelImpl.MainFragment
    ): View.OnClickListener = View.OnClickListener {

        when (fragmentCurrent) {
            GLOBAL -> {

                clickListener.onClick(GLOBAL)

                dismiss()
            }
            CLOUD -> {

                clickListener.onClick(CLOUD)

                dismiss()
            }
            else -> {

                clickListener.onClick(GLOBAL)
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