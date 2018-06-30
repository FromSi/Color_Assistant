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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_constructor.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.interfaces.OnClickItemColorListener
import kz.sgq.colorassistant.ui.view.ItemColor

class ConstructorFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_constructor, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingToolBar()

        color_picker.addLightnessView(lightness)
        color_picker.addSaturationView(saturation)
        createNewItemColor()
        createNewItemColor()
        createNewItemColor()
        color_picker.setItemColor(item_list.getChildAt(0) as ItemColor)
    }

    private fun createNewItemColor(){
        val itemColor = ItemColor(context)
        itemColor.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        item_list.addView(itemColor)

        itemColor.setOnClickItemColorListener(object : OnClickItemColorListener{
            override fun onClick() {
                color_picker.setItemColor(itemColor)
            }
        })
    }

    private fun settingToolBar() {
        val actionBar = (activity as AppCompatActivity)
        toolBar.title = getString(R.string.constructor)
        actionBar.setSupportActionBar(toolBar)
    }
}