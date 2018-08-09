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
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_hsl.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.RecyclerDetailsAdapter
import kz.sgq.colorassistant.ui.util.ItemDetails
import android.util.DisplayMetrics
import android.widget.ImageButton

class HSLBottomSheet : BottomSheetDialogFragment() {
    private lateinit var title: String
    private lateinit var list: MutableList<ItemDetails>
    private lateinit var button: ImageButton

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        val view = View.inflate(context, R.layout.bottom_sheet_hsl, null)

        dialog!!.setContentView(view)

        val bottomSheet = BottomSheetBehavior.from(view.parent as View)

        if (bottomSheet != null) {
            val diametric = DisplayMetrics()

            activity!!.windowManager.defaultDisplay.getMetrics(diametric)

            bottomSheet.peekHeight = diametric.heightPixels / 2
            view.title.text = title
            button = view.exit

            button.setOnClickListener(initClickExit())
            initList(view)
            bottomSheet.setBottomSheetCallback(initCallback())
            view.requestLayout()
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setList(list: MutableList<ItemDetails>) {
        this.list = list
    }

    private fun initClickExit(): View.OnClickListener = View.OnClickListener { dismiss() }

    private fun initList(view: View) {
        val adapter = RecyclerDetailsAdapter()
        val linearLayoutManager = LinearLayoutManager(view.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        view.list.layoutManager = linearLayoutManager
        view.list.adapter = adapter
        adapter.addList(list)
    }

    private fun initCallback(): BottomSheetBehavior.BottomSheetCallback =
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {

                }

                override fun onStateChanged(p0: View, p1: Int) {

                    if (p1 == BottomSheetBehavior.STATE_HIDDEN)
                        dismiss()

                    if (p1 == BottomSheetBehavior.STATE_COLLAPSED)
                        button.visibility = View.GONE

                    if (p1 == BottomSheetBehavior.STATE_EXPANDED)
                        button.visibility = View.VISIBLE
                }
            }
}