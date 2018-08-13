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

import android.app.Activity
import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_constructor.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.ConstructorPresenter
import kz.sgq.colorassistant.mvp.view.ConstructorView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.fragment.dialog.DeleteDialog
import kz.sgq.colorassistant.ui.fragment.dialog.SaveDialog
import kz.sgq.colorassistant.ui.fragment.sheet.InfoColorBottomSheet
import kz.sgq.colorassistant.ui.view.ItemColor
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class ConstructorActivity : MvpAppCompatActivity(), ConstructorView {
    @InjectPresenter
    lateinit var presenter: ConstructorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val helper = SwipeBackActivityHelper(this)

        helper.onActivityCreate()
        setContentView(R.layout.activity_constructor)
        helper.swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        initActionBar()
        initColorPicker()
        helper.onPostCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun finishActivity(bool: Boolean) {

        if (bool) {

            setResult(Activity.RESULT_OK, null)
            finish()
        }
    }

    private fun initActionBar() {
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener {
            val dialog = SaveDialog()

            dialog.clickListener(initClickSave())
            dialog.show(supportFragmentManager, "save_dialog")
        }
    }

    private fun getColorHex(index: Int): String = (item_list.getChildAt(index) as ItemColor)
            .getColorHex()

    private fun initClickSave(): SaveDialog.OnClickListener = object : SaveDialog.OnClickListener {
        override fun onClick() {
            val cloud = Cloud(
                    getColorHex(0),
                    getColorHex(1),
                    getColorHex(2)
            )

            if (item_list.childCount >= 4)
                cloud.colFour = getColorHex(3)

            if (item_list.childCount >= 5)
                cloud.colFour = getColorHex(4)

            presenter.save(cloud)
        }
    }

    private fun initColorPicker() {

        color_picker.addLightnessView(lightness)
        color_picker.addSaturationView(saturation)

        for (i in 0..2)
            createNewItemColor()

        color_picker.setItemColor(item_list.getChildAt(0) as ItemColor)
        add.setOnClickListener(initClickAdd())
    }

    private fun initClickAdd(): View.OnClickListener = View.OnClickListener {
        ItemColor.ItemColor.boolDelete = true

        if (item_list.childCount <= 3)
            createNewItemColor()
        else {
            add.visibility = View.GONE

            createNewItemColor()
        }
    }

    private fun createNewItemColor() {
        val itemColor = ItemColor(this)
        itemColor.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

        item_list.addView(itemColor)
        itemColor.setDeleteIndex(item_list.childCount - 1)
        itemColor.setOnItemColorListener(initItemColor())
        itemColor.setOnClickItemColorListener(initItemClickColor(itemColor))
    }

    private fun initItemClickColor(
            itemColor: ItemColor
    ): ItemColor.OnClickListener = object : ItemColor.OnClickListener {
        override fun onClick() {

            color_picker.setItemColor(itemColor)
        }
    }

    private fun initItemColor(): ItemColor.OnItemListener = object : ItemColor.OnItemListener {
        override fun onInfo(color: Int) {
            initInfoSheet(color)
        }

        override fun onDelete(index: Int) {
            val dialog = DeleteDialog()

            dialog.clickListener(index, object : DeleteDialog.OnDeleteListener {
                override fun onDelete(index: Int) {

                    item_list.removeViewAt(index)

                    add.visibility = View.VISIBLE

                    if (item_list.childCount <= 3)
                        ItemColor.ItemColor.boolDelete = false

                    for (i in index until item_list.childCount)
                        (item_list.getChildAt(i) as ItemColor).setDeleteIndex(i)

                    color_picker.setItemColor(item_list.getChildAt(0) as ItemColor)
                }
            })

            dialog.show(supportFragmentManager, "delete_dialog")
        }
    }

    private fun initInfoSheet(color: Int) {
        val dialog = InfoColorBottomSheet()

        dialog.setColor(color)
        dialog.show(supportFragmentManager, "info_color_bottom_sheet")
    }
}