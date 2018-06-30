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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_info.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.InfoPresenter
import kz.sgq.colorassistant.mvp.view.InfoView
import kz.sgq.colorassistant.ui.adapters.SectionsPageAdapter
import kz.sgq.colorassistant.ui.util.ItemDetails

class InfoFragment : MvpAppCompatFragment(), InfoView {
    @InjectPresenter
    lateinit var presenter:InfoPresenter

    private lateinit var adapter: SectionsPageAdapter
    private val saturation = DetailsFragment()
    private val lightness = DetailsFragment()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(saturation, getString(R.string.fragmentSaturationName))
        adapter.addFragment(lightness, getString(R.string.fragmentLightnessName))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun createSaturation(color: Int, itemList: MutableList<ItemDetails>) {
        saturation.initAdapter(itemList)
    }

    override fun createLightness(color: Int, itemList: MutableList<ItemDetails>) {
        lightness.initAdapter(itemList)
    }

    override fun installViewPager() {
    }

    fun initPresenter(color: Int){
        presenter.initColor(color)
//        presenter.initAllView()
    }
}