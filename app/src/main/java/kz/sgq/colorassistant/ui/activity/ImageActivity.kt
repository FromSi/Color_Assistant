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
import kz.sgq.colorassistant.R
import android.content.Intent
import android.provider.MediaStore
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_image.*
import kz.sgq.colorassistant.mvp.presenter.ImagePresenter
import kz.sgq.colorassistant.mvp.view.ImageView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.RecyclerImageAdapter
import kz.sgq.colorassistant.ui.fragment.dialog.ImageMoreDialog
import kz.sgq.colorassistant.ui.fragment.dialog.ShareDialog

class ImageActivity : MvpAppCompatActivity(), ImageView {
    @InjectPresenter
    lateinit var presenter: ImagePresenter
    private lateinit var adapter: RecyclerImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image)
        initToolBar()
        initScan()
        setResult(Activity.RESULT_OK, null)
        openGallery()
    }

    override fun finishActivity() {

        setResult(Activity.RESULT_CANCELED, null)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.initImage(resultCode, data)
    }

    override fun initItemsColor(list: MutableList<MutableList<Int>>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        colors.layoutManager = layoutManager
        adapter = RecyclerImageAdapter()
        colors.adapter = adapter

        adapter.initColors(list)
        initClick()
        setVisibly(true)
    }

    override fun initImage(photoUri: Uri) {

        try {
            val currentImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)

            image.setImageBitmap(currentImage)
            presenter.setCurrentImage(currentImage)
            setVisibly(false)
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    override fun showMore(cloud: Cloud) {
        val dialog = ImageMoreDialog()

        dialog.cloud(cloud)
        dialog.show(supportFragmentManager, "image_more_dialog")
    }

    private fun setVisibly(bool: Boolean) {

        if (bool) {
            loading.visibility = View.GONE
            colors.visibility = View.VISIBLE
        } else {
            loading.visibility = View.VISIBLE
            colors.visibility = View.GONE
        }
    }

    private fun initToolBar() {
        toolBar.title = resources.getString(R.string.image_scan)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"

        startActivityForResult(photoPickerIntent, 1)
    }

    private fun initScan() {

        scan.setOnClickListener {

            openGallery()
        }
    }

    private fun initClick(){
        adapter.setClickListener(object : RecyclerImageAdapter.OnClickListener{
            override fun show(index: Int) {

                presenter.showMore(index)
            }

            override fun onSave(index: Int) {

                presenter.cloudSave(index)
            }

            override fun onDelete(index: Int) {

                presenter.cloudDelete(index)
            }

            override fun onShare(share: String) {
                val dialog = ShareDialog()

                dialog.setText(share)
                dialog.show(supportFragmentManager, "share_dialog")
            }
        })
    }
}
