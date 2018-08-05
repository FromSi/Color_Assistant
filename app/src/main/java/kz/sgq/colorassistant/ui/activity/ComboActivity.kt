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

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_combo.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.SectionsPageAdapter
import kz.sgq.colorassistant.ui.fragment.ComboFragment
import kz.sgq.colorassistant.ui.fragment.InfoFragment
import kz.sgq.colorassistant.ui.util.interfaces.OnClickActivity
import android.support.v4.view.ViewPager
import android.view.MenuItem

class ComboActivity : AppCompatActivity(), OnClickActivity {
    private var adapter = SectionsPageAdapter(supportFragmentManager)
    private val comboFragment = ComboFragment()
    private val infoFragment = InfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combo)
        toolBar.title = getString(R.string.combo)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settingToolBar()
        initFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initFragment() {
        val list = intent
                .getSerializableExtra("map") as MutableList<String>
        comboFragment.initPresenter(list)
        adapter.addFragment(comboFragment, getString(R.string.combo))
        adapter.addFragment(infoFragment, getString(R.string.info))
        viewPager.adapter = adapter
    }

    override fun onInfo(color: Int) {
        infoFragment.initPresenter(color)
        viewPager.currentItem = 1
        viewPager.disableScroll(false)
    }

    private fun settingToolBar() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> toolBar.title = getString(R.string.combo)
                    1 -> toolBar.title = getString(R.string.info)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}
