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

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_beta_main.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.model.MainModelImpl
import kz.sgq.colorassistant.mvp.presenter.MainPresenter
import kz.sgq.colorassistant.mvp.view.MainView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.fragment.dialog.QRScanDialog
import kz.sgq.colorassistant.ui.fragment.sheet.MenuBottomSheet
import kz.sgq.colorassistant.ui.view.ItemColor

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
        R.id.settings -> {
            val intent = Intent(this, SettingsActivity::class.java)

            startActivity(intent)

            true
        }
        R.id.qr -> {

            checkCameraPermission()

            true
        }
        R.id.image_scan -> {
            val intent = Intent(this, ImageActivity::class.java)

            startActivityForResult(intent, 2)

            true
        }
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

    override fun answerQR(cloud: Cloud) {
        val dialog = QRScanDialog()

        dialog.cloud(cloud)
        dialog.clickListener(initClickAnswer(cloud))
        dialog.show(supportFragmentManager, "qr_dialog")
    }

    override fun errorQR() {
        val snack = Snackbar
                .make(
                        fab,
                        resources.getString(R.string.snack_qr_scan_error_title),
                        Snackbar.LENGTH_LONG
                )
                .setAction(
                        resources.getString(R.string.snack_qr_scan_error_click),
                        initClickError()
                )
                .setActionTextColor(resources.getColor(R.color.snack_error))

        snack.view
                .findViewById<TextView>(android.support.design.R.id.snackbar_text)
                .setTextColor(resources.getColor(R.color.snack_text))
        snack.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
            presenter.initResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 10) {
            val permission = permissions[0]
            val grantResult = grantResults[0]

            if (permission == Manifest.permission.CAMERA && grantResult == PackageManager.PERMISSION_GRANTED)
                openScanActivity()
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initClickAnswer(
            cloud: Cloud
    ): ItemColor.OnClickListener = object : ItemColor.OnClickListener {
        override fun onClick() {

            presenter.save(cloud)
        }
    }

    private fun initClickError(): View.OnClickListener = View.OnClickListener {

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    parent,
                    arrayOf(Manifest.permission.CAMERA),
                    10
            )
        else
            openScanActivity()
    }

    private fun openScanActivity() {
        val intent = Intent(this, QRCodeScanActivity::class.java)

        startActivityForResult(intent, 1)
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
