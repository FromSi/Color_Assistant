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

package kz.sgq.colorassistant.mvp.presenter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.ImageModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.ImageModel
import kz.sgq.colorassistant.mvp.view.ImageView
import kz.sgq.colorassistant.ui.util.interfaces.OnClickListener

@InjectViewState
class ImagePresenter : MvpPresenter<ImageView>() {
    private var model: ImageModel = ImageModelImpl()

    fun initImage(resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            val photoUri = data?.data

            if (photoUri != null)
                viewState.initImage(photoUri)

        } else if (!model.getState())
            viewState.finishActivity()

    }

    fun showMore(index: Int) {

        viewState.showMore(model.getCloud(index))
    }

    fun setCurrentImage(currentImage: Bitmap) {

        model.setState(true)
        model.setCurrentImage(currentImage, object : OnClickListener {

            override fun onClick() {

                viewState.initItemsColor(model.getColorList())
            }
        })
    }

    fun cloudSave(index: Int) {

        model.saveCloud(index)
    }

    fun cloudDelete(index: Int) {

        model.deleteCloud(index)
    }
}