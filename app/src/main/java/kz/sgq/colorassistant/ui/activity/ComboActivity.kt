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
import kotlinx.android.synthetic.main.activity_combo.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.SectionsPageAdapter
import kz.sgq.colorassistant.ui.fragment.ComboFragment
import kz.sgq.colorassistant.ui.fragment.InfoFragment
import kz.sgq.colorassistant.ui.util.interfaces.OnClickActivity
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.sgq.colorassistant.mvp.presenter.ComboPresenter
import kz.sgq.colorassistant.mvp.view.ComboView
import kz.sgq.colorassistant.ui.fragment.dialog.ShareDialog

class ComboActivity : MvpAppCompatActivity(), ComboView, OnClickActivity {
    private var adapter = SectionsPageAdapter(supportFragmentManager)
    private val comboFragment = ComboFragment()
    private val infoFragment = InfoFragment()

    @InjectPresenter
    lateinit var presenter: ComboPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_combo)
        initToolBar()
        presenter.initList(intent.getSerializableExtra("map") as MutableList<String>)
        settingToolBar()
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


    override fun onInfo(color: Int) {
        viewPager.currentItem = 1

        infoFragment.initPresenter(color)
        viewPager.disableScroll(false)
    }

    override fun showShare(textShare: String) {
        val dialog = ShareDialog()

        dialog.setText(textShare)
        dialog.show(supportFragmentManager, "share_dialog")
    }

    override fun initFragment(list: MutableList<String>) {

        comboFragment.initPresenter(list)
        adapter.addFragment(comboFragment, getString(R.string.combo))
        adapter.addFragment(infoFragment, getString(R.string.info))

        viewPager.adapter = adapter
    }

    private fun settingToolBar() {

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> toolBar.title = getString(R.string.combo)
                    1 -> toolBar.title = getString(R.string.info)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun initToolBar() {
        toolBar.title = getString(R.string.combo)

        toolBar.inflateMenu(R.menu.combo_menu)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
