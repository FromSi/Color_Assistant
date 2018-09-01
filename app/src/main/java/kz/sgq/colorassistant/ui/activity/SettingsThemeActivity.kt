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

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_settings_theme.*
import kotlinx.android.synthetic.main.f_settings_theme.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.SettingsThemePresenter
import kz.sgq.colorassistant.mvp.view.SettingsThemeView
import kz.sgq.colorassistant.ui.util.java.ThemeEnum
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
        initItems()
        initClickMenu()
        helper.onPostCreate()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initItems() {
        var index = 0

        for (i in 0 until mode.childCount)
            if ((i % 2) == 0)
                mode.getChildAt(i).title.text = resources
                        .getStringArray(R.array.settings_theme_night_title)[index]

        index = 0

        for (i in 0 until color.childCount)
            if ((i % 2) == 0)
                color.getChildAt(i).apply {
                    title.text = resources.getStringArray(R.array.settings_theme_color_title)[index]
                    button.buttonTintList = ColorStateList(
                            arrayOf(IntArray(0)),
                            intArrayOf(resources.getIntArray(R.array.settings_theme_color)[index++])
                    )
                }
    }

    private fun initClickMenu() {

        for (i in 0 until mode.childCount)
            if ((i % 2) == 0) {

                mode.getChildAt(i).setOnClickListener {

                    for (j in 0 until mode.childCount)
                        if ((j % 2) == 0)
                            mode.getChildAt(j).button.isChecked = false

                    mode.getChildAt(i).button.isChecked = true

                    getSharedPreferences("settings", Context.MODE_PRIVATE)
                            .edit()
                            .putString(
                                    "night_mode",
                                    resources.getStringArray(R.array.settings_theme_night_title)[i / 2]
                            )
                            .apply()
                }
            }

        for (i in 0 until color.childCount)
            if ((i % 2) == 0) {

                color.getChildAt(i).setOnClickListener {

                    for (j in 0 until color.childCount)
                        if ((j % 2) == 0)
                            color.getChildAt(j).button.isChecked = false

                    color.getChildAt(i).button.isChecked = true

                    getSharedPreferences("settings", Context.MODE_PRIVATE)
                            .edit()
                            .putString(
                                    "theme_color",
                                    resources.getStringArray(R.array.settings_theme_color_title)[i / 2]
                                            .replace(' ', '_')
                                            .toUpperCase()
                            )
                            .apply()
                }
            }
    }
}
