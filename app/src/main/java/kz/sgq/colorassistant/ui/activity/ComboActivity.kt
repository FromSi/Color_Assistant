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

package kz.sgq.colorassistant.ui.activity

import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.support.v4.widget.NestedScrollView
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_combo.*
import kotlinx.android.synthetic.main.item_combo_color.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.ComboPresenter
import kz.sgq.colorassistant.mvp.view.ComboView
import kz.sgq.colorassistant.ui.fragment.sheet.ComboListBottomSheet
import kz.sgq.colorassistant.ui.fragment.sheet.HSLBottomSheet
import kz.sgq.colorassistant.ui.fragment.dialog.ShareDialog
import kz.sgq.colorassistant.ui.util.ItemDetails
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class ComboActivity : MvpAppCompatActivity(), ComboView {
    @InjectPresenter
    lateinit var presenter: ComboPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        setContentView(R.layout.activity_combo)
        presenter.initColorList(intent.getSerializableExtra("map") as MutableList<String>)
        initActionBar()
        SwipeBackActivityHelper(this)
                .apply {

                    onActivityCreate()
                    swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
                    onPostCreate()
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun setBackgroundColor(color: Int) {

        constructor.setBackgroundColor(color)
    }

    override fun setTextColor(color: Int) {

        text.setTextColor(color)
    }

    override fun initCardClick(size: Int) {

        for (i in 0 until size)
            list_card.getChildAt(i)
                    .setOnClickListener { presenter.cardClick(i) }
    }

    override fun openShare(share: String) {

        ShareDialog().apply {
            setText(share)
            show(supportFragmentManager, "share_dialog")
        }
    }

    override fun cardClick(index: Int) {

        ComboListBottomSheet().apply {

            setClick(object : ComboListBottomSheet.OnClickListener {
                override fun onClickSaturation() {

                    presenter.openSaturation(index)
                }

                override fun onClickLightness() {

                    presenter.openLightness(index)
                }
            })
            show(supportFragmentManager, "combo_list_bottom_sheet")
        }
    }

    override fun openHSLSheet(list: MutableList<ItemDetails>) {

        HSLBottomSheet().apply {

            setTitle(resources.getString(R.string.bottom_sheet_hsl_saturation))
            setList(list)
            show(supportFragmentManager, "hsl_bottom_sheet")
        }
    }

    override fun initHeader(i: Int, j: Int, color: Int) {

        list_card.getChildAt(i).list.getChildAt(j).apply {
            visibility = View.VISIBLE
            setBackgroundColor(color)
        }
    }

    override fun initItemBackground(i: Int, color: Int) {

        list_card.getChildAt(i).imgColor.setBackgroundColor(color)
        initExample(i, color)
    }

    override fun handlerVisibly(size: Int) {

        for (i in 0 until size)
            list_card.getChildAt(i).visibility = View.VISIBLE
    }

    override fun initItemValue(index: Int, value: String) {
        list_card.getChildAt(index).value.text = value
    }

    override fun initClicks(size: Int) {

        for (i in 0 until size)
            list_item_bg.getChildAt(i).setOnClickListener {
                presenter.handlerBackgroundColor(i)
            }

        for (i in 0 until size)
            list_item_text.getChildAt(i).setOnClickListener {
                presenter.handlerTextColor(i)
            }
    }

    private fun initActionBar() {

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener {

            presenter.showShare()
        }
    }

    private fun initExample(index: Int, color: Int) {
        list_item_bg.getChildAt(index).visibility = View.VISIBLE
        list_item_text.getChildAt(index).visibility = View.VISIBLE

        list_item_bg.getChildAt(index).setBackgroundColor(color)
        list_item_text.getChildAt(index).setBackgroundColor(color)

        if (index == 0)
            constructor.setBackgroundColor(color)

        if (index == 1)
            text.setTextColor(color)
    }
}