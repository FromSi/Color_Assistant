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
import android.view.Menu
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

class ComboActivity : MvpAppCompatActivity(), ComboView {
    @InjectPresenter
    lateinit var presenter: ComboPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_combo)
        presenter.initColorList(intent.getSerializableExtra("map") as MutableList<String>)
        initToolBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.combo_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()

            true
        }
        R.id.share -> {

            presenter.showShare()

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun setBackgroundColor(color: Int) {

        background.setBackgroundColor(color)
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
        val dialog = ShareDialog()

        dialog.setText(share)
        dialog.show(supportFragmentManager, "share_dialog")
    }

    override fun cardClick(index: Int) {
        val dialog = ComboListBottomSheet()

        dialog.setClick(object : ComboListBottomSheet.OnClickListener{
            override fun onClickSaturation() {

                presenter.openSaturation(index)
            }

            override fun onClickLightness() {

                presenter.openLightness(index)
            }
        })
        dialog.show(supportFragmentManager, "combo_list_bottom_sheet")
    }

    override fun openSaturation(list: MutableList<ItemDetails>) {
        val dialog = HSLBottomSheet()

        dialog.setTitle(resources.getString(R.string.bottom_sheet_hsl_saturation))
        dialog.setList(list)
        dialog.show(supportFragmentManager, "hsl_bottom_sheet")
    }

    override fun openLightness(list: MutableList<ItemDetails>) {
        val dialog = HSLBottomSheet()

        dialog.setTitle(resources.getString(R.string.bottom_sheet_hsl_lightness))
        dialog.setList(list)
        dialog.show(supportFragmentManager, "hsl_bottom_sheet")
    }

    override fun initHeader(i: Int, j: Int, color: Int) {
        list_card.getChildAt(i).list.getChildAt(j).visibility = View.VISIBLE

        list_card.getChildAt(i).list.getChildAt(j).setBackgroundColor(color)
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

    override fun initClicks(size: Int){

        for (i in 0 until size)
            list_item_bg.getChildAt(i).setOnClickListener {
                presenter.handlerBackgroundColor(i)
            }

        for (i in 0 until size)
            list_item_text.getChildAt(i).setOnClickListener {
                presenter.handlerTextColor(i)
            }
    }

    private fun initToolBar() {
        toolbar.title = getString(R.string.toolbar_combo)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initExample(index: Int, color: Int) {
        list_item_bg.getChildAt(index).visibility = View.VISIBLE
        list_item_text.getChildAt(index).visibility = View.VISIBLE

        list_item_bg.getChildAt(index).setBackgroundColor(color)
        list_item_text.getChildAt(index).setBackgroundColor(color)

        if (index == 0)
            background.setBackgroundColor(color)

        if (index == 1)
            text.setTextColor(color)
    }
}