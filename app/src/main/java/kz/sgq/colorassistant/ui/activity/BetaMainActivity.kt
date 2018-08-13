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
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_beta_main.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.mvp.presenter.MainPresenter
import kz.sgq.colorassistant.mvp.view.MainView
import kz.sgq.colorassistant.ui.fragment.sheet.MenuBottomSheet

class BetaMainActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_beta_main)
        initActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            presenter.openMenu()

            true
        }
        R.id.settings -> true
        R.id.qr -> true
        R.id.image_scan -> true
        else -> super.onOptionsItemSelected(item)
    }

    override fun global() {

        presenter.setCurrentFragment(MainModelImpl.MainFragment.GLOBAL)
        fab.setImageDrawable(resources.getDrawable(R.drawable.like))
    }

    override fun like() {

        presenter.setCurrentFragment(MainModelImpl.MainFragment.LIKE)
        fab.setImageDrawable(resources.getDrawable(R.drawable.cancel))
    }

    override fun cloud() {

        presenter.setCurrentFragment(MainModelImpl.MainFragment.CLOUD)
        fab.setImageDrawable(resources.getDrawable(R.drawable.constructor))
    }

    override fun constructor() {


    }

    override fun cancel() {

        presenter.setCurrentFragment(MainModelImpl.MainFragment.LIKE)
        fab.setImageDrawable(resources.getDrawable(R.drawable.like))
    }

    override fun openMenu(fragmentCurrent: MainModelImpl.MainFragment) {
        val dialog = MenuBottomSheet()

        dialog.setFragmentCurrent(fragmentCurrent)
        dialog.setClick(object : MenuBottomSheet.OnClickListener {
            override fun onClick(fragmentCurrent: MainModelImpl.MainFragment) {

                presenter.setCurrentFragment(fragmentCurrent)
                presenter.menuClick()
            }
        })
        dialog.show(supportFragmentManager, "menu_bottom_sheet")
    }

    private fun initActionBar(){

        setSupportActionBar(bar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }
        fab.setOnClickListener {

            presenter.fabClick()
        }
    }
}
