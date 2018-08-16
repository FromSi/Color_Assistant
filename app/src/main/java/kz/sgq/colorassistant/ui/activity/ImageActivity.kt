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
import android.support.design.bottomappbar.BottomAppBar
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
import kz.sgq.colorassistant.ui.fragment.dialog.MoreDialog
import kz.sgq.colorassistant.ui.fragment.dialog.ShareDialog
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class ImageActivity : MvpAppCompatActivity(), ImageView {
    @InjectPresenter
    lateinit var presenter: ImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        setContentView(R.layout.activity_image)
        initActionBar()
        setResult(Activity.RESULT_OK, null)
        openGallery()
        SwipeBackActivityHelper(this)
                .apply {

                    onActivityCreate()
                    swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
                    onPostCreate()
                }
    }

    override fun finishActivity() {

        setResult(Activity.RESULT_CANCELED, null)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.initImage(resultCode, data)
    }

    override fun initItemsColor(list: MutableList<MutableList<Int>>) {

        LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
            colors.layoutManager = this
        }
        RecyclerImageAdapter().apply {
            colors.adapter = this

            initColors(list)
            initClick(this)
        }
        setVisibly(true)
    }

    override fun initImage(photoUri: Uri) {

        try {

            MediaStore.Images.Media
                    .getBitmap(this.contentResolver, photoUri)
                    .apply {

                        image.setImageBitmap(this)
                        presenter.setCurrentImage(this)
                    }
            setVisibly(false)
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    override fun showMore(cloud: Cloud) {
        MoreDialog().apply {

            cloud(cloud)
            show(supportFragmentManager, "image_more_dialog")
        }
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

    private fun initActionBar() {
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener {

            openGallery()
        }
    }

    private fun openGallery() {

        startActivityForResult(
                Intent(Intent.ACTION_PICK).apply { type = "image/*" },
                1
        )
    }

    private fun initClick(adapter: RecyclerImageAdapter) {
        adapter.setClickListener(object : RecyclerImageAdapter.OnClickListener {
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

                ShareDialog().apply {

                    setText(share)
                    show(supportFragmentManager, "share_dialog")
                }
            }
        })
    }
}
