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

package kz.sgq.colorassistant.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_cloud.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.fragment.CloudPresenter
import kz.sgq.colorassistant.mvp.view.fragment.CloudView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.activity.ImageActivity
import kz.sgq.colorassistant.ui.activity.QRCodeScanActivity
import kz.sgq.colorassistant.ui.adapters.RecyclerCloudAdapter
import kz.sgq.colorassistant.ui.fragment.dialog.*
import kz.sgq.colorassistant.ui.view.ItemColor
import java.io.Serializable
import android.support.design.widget.Snackbar
import android.widget.TextView
import kz.sgq.colorassistant.ui.activity.ComboActivity
import kz.sgq.colorassistant.ui.activity.ConstructorActivity
import kz.sgq.colorassistant.ui.adapters.holders.CloudHolder

class CloudFragment : MvpAppCompatFragment(), CloudView {
    private var adapter = RecyclerCloudAdapter()

    @InjectPresenter
    lateinit var presenter: CloudPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cloud, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingToolBar()
        initRecyclerAdapter(view.context)
    }

    override fun initColorList(list: MutableList<Cloud>) {

        adapter.addList(list)
    }

    override fun addItem(cloud: Cloud) {

        adapter.addItem(cloud)
    }

    override fun deleteItem(index: Int) {

        adapter.deleteItem(index)
    }

    override fun errorQR() {
        val snack = Snackbar.make(
                view!!,
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

    override fun shareItem(text: String) {
        val dialog = ShareDialog()

        dialog.setText(text)
        dialog.show(fragmentManager, "share_dialog")
    }

    override fun showActivityInfo(list: MutableList<String>) {
        val intent = Intent(context, ComboActivity::class.java)

        intent.putExtra("map", list as Serializable)
        startActivity(intent)
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

    override fun answerQR(cloud: Cloud) {
        val dialog = QRScanDialog()

        dialog.cloud(cloud)
        dialog.clickListener(initClickAnswer(cloud))
        dialog.show(fragmentManager, "qr_dialog")
    }

    private fun initClickError(): View.OnClickListener = View.OnClickListener {

        checkCameraPermission()
    }

    private fun initClickAnswer(
            cloud: Cloud
    ): ItemColor.OnClickListener = object : ItemColor.OnClickListener {
        override fun onClick() {

            presenter.save(cloud)
        }
    }

    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    10
            )
        else
            openScanActivity()
    }

    private fun openScanActivity() {
        val intent = Intent(context, QRCodeScanActivity::class.java)

        startActivityForResult(intent, 1)
    }

    private fun initRecyclerAdapter(context: Context) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        cloud.layoutManager = layoutManager
        cloud.adapter = adapter

        clickListener()
        presenter.initInitList()
    }

    private fun clickListener() {
        adapter.setOnItemClickListener(object : CloudHolder.OnClickListener {
            override fun onView(cloud: Cloud) {

                presenter.onItemViewClick(cloud)
            }

            override fun onShare(cloud: Cloud) {

                presenter.onItemShareClick(cloud)
            }

            override fun onDelete(cloud: Cloud, index: Int) {

                presenter.onItemDeleteClick(cloud, index)
            }
        })
    }

    private fun settingToolBar() {
        bar.title = getString(R.string.toolbar_constructor)

        bar.inflateMenu(R.menu.cloud_menu)
        bar.setOnMenuItemClickListener(initClickMenu())
    }

    private fun initClickMenu(): Toolbar.OnMenuItemClickListener = Toolbar.OnMenuItemClickListener {

        when (it.itemId) {
            R.id.constructor -> {
                val intent = Intent(context!!, ConstructorActivity::class.java)

                startActivityForResult(intent, 2)
            }
            R.id.qr -> {

                checkCameraPermission()
            }
            R.id.image_scan -> {
                val intent = Intent(this.context, ImageActivity::class.java)

                startActivityForResult(intent, 2)
            }
        }

        false
    }
}