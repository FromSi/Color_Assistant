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
import kz.sgq.colorassistant.ui.adapters.RecyclerImageAdapter
import kz.sgq.colorassistant.ui.fragment.dialog.ShareDialog
import kz.sgq.colorassistant.ui.util.interfaces.OnSaveListener
import kz.sgq.colorassistant.ui.util.interfaces.OnShareListener

class ImageActivity : MvpAppCompatActivity(), ImageView {
    @InjectPresenter
    lateinit var presenter: ImagePresenter

    private var adapter = RecyclerImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        toolBar.title = resources.getString(R.string.image_scan)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setResult(Activity.RESULT_OK, null)

        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 1)
    }

    override fun finishActivity() {
        setResult(Activity.RESULT_CANCELED, null)
        finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.initImage(resultCode, data)
    }

    override fun initItemsColor(list: MutableList<MutableList<Int>>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        colors.layoutManager = layoutManager
        colors.adapter = adapter
        adapter.initColors(list)

        initSave()
        initShare()

        loading.visibility = View.GONE
        colors.visibility = View.VISIBLE
    }

    override fun initImage(photoUri: Uri) {
        try {
            val currentImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            image.setImageBitmap(currentImage)
            presenter.setCurrentImage(currentImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initSave(){
        adapter.setSaveListener(object : OnSaveListener{
            override fun onSave(index: Int) {
                presenter.cloudSave(index)
            }

            override fun onDelete(index: Int) {
                presenter.cloudDelete(index)
            }

        })
    }

    private fun initShare(){
        adapter.setShareListener(object : OnShareListener{
            override fun onShare(share: String) {
                val dialog = ShareDialog()
                dialog.setText(share)
                dialog.show(supportFragmentManager, "share_dialog")
            }

        })
    }
}
