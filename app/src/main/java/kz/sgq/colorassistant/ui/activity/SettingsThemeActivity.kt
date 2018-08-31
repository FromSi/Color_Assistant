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
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.SettingsThemePresenter
import kz.sgq.colorassistant.mvp.view.SettingsThemeView
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class SettingsThemeActivity : MvpAppCompatActivity(), SettingsThemeView {
    @InjectPresenter
    lateinit var presenter: SettingsThemePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val helper = SwipeBackActivityHelper(this)

        helper.onActivityCreate()
        setContentView(R.layout.activity_settings_theme)
        helper.swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        //code
        helper.onPostCreate()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
