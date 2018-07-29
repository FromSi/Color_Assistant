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
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_cloud.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.CloudPresenter
import kz.sgq.colorassistant.mvp.view.CloudView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.RecyclerCloudAdapter
import kz.sgq.colorassistant.ui.util.interfaces.OnClickItemColorListener
import kz.sgq.colorassistant.ui.view.ItemColor
import kz.sgq.colorassistant.ui.fragment.dialog.SaveFragment
import kz.sgq.colorassistant.ui.util.interfaces.OnClickListener
import kz.sgq.colorassistant.ui.util.interfaces.OnItemCloudClickListener

class CloudFragment : MvpAppCompatFragment(), CloudView {

    @InjectPresenter
    lateinit var presenter: CloudPresenter

    private var adapter = RecyclerCloudAdapter()

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

        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        cloud.layoutManager = layoutManager
        cloud.adapter = adapter
        clickListener()

        presenter.initInitList()

        color_picker.addLightnessView(lightness)
        color_picker.addSaturationView(saturation)
        createNewItemColor()
        createNewItemColor()
        createNewItemColor()
        color_picker.setItemColor(item_list.getChildAt(0) as ItemColor)
    }

    override fun initColorList(list: MutableList<Cloud>) {
        Log.d("TestList", "Yes")
        adapter.addList(list)
    }

    override fun addItem(cloud: Cloud) {
        Log.d("TestAddItemInRoomORM", "Added item")
        adapter.addItem(cloud)
    }

    private fun createNewItemColor() {
        val itemColor = ItemColor(context)
        itemColor.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        item_list.addView(itemColor)

        itemColor.setOnClickItemColorListener(object : OnClickItemColorListener {
            override fun onClick() {
                color_picker.setItemColor(itemColor)
            }
        })
    }

    private fun clickListener() {
        adapter.setOnItemClickListener(object : OnItemCloudClickListener {
            override fun viewClick(cloud: Cloud) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shareClick(cloud: Cloud) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun deleteClick(cloud: Cloud) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun settingToolBar() {
//        val actionBar = (activity as AppCompatActivity)
        toolBar.title = getString(R.string.constructor)
        toolBar.inflateMenu(R.menu.constructor_menu)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.constructor -> {
//                    constructor.visibility = View.VISIBLE
//                    cloud.visibility = View.VISIBLE
//                    cloud.visibility = View.GONE
                }
                R.id.cloud -> {
//                    Log.d("TAGtEST", "cloud")
//                    toolBar.menu.findItem(R.id.constructor).isVisible = false
//                    constructor.visibility = View.GONE
//                    cloud.visibility = View.VISIBLE
                }
                R.id.save -> {
                    val dialog = SaveFragment()
                    dialog.clickListener(object : OnClickListener {
                        override fun onClick() {
                            presenter.addItem(Cloud("#323232", "#323232", "#323232", "#323232", "#323232"))
                        }

                    })
                    dialog.show(fragmentManager, "save_dialog")
                }
            }
            false
        }
//        actionBar.setSupportActionBar(toolBar)
    }
}