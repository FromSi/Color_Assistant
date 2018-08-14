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

import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.MenuPageAdapter
import kz.sgq.colorassistant.ui.fragment.CloudFragment
import kz.sgq.colorassistant.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupViewPager()
        setupTab()
    }

    private fun setupViewPager() {
        val menu = MenuPageAdapter(supportFragmentManager)

        menu.addFragment(MainFragment())
        menu.addFragment(CloudFragment())
        menu.addFragment(CloudFragment())

        view_pager.adapter = menu
    }

    private fun setupTab() {

        tab_layout.setupWithViewPager(view_pager)
        tab_layout.getTabAt(0)?.icon = ContextCompat.getDrawable(this, R.drawable.global)
        tab_layout.getTabAt(1)?.icon = ContextCompat.getDrawable(this, R.drawable.cloud)
        tab_layout.getTabAt(2)?.icon = ContextCompat.getDrawable(this, R.drawable.settings)
        tab_layout.getTabAt(0)?.icon?.setColorFilter(
                ContextCompat.getColor(this, R.color.icon),
                PorterDuff.Mode.SRC_IN
        )
        tabListener()
    }

    private fun tabListener() {

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val colorDef = ContextCompat.getColor(applicationContext, R.color.icon_def)

                tab?.icon?.setColorFilter(colorDef, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val color = ContextCompat.getColor(applicationContext, R.color.icon)

                tab?.icon?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        })
    }
}
