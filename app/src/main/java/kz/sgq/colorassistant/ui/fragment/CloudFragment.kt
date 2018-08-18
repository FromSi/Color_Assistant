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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_cloud.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.fragment.CloudPresenter
import kz.sgq.colorassistant.mvp.view.fragment.CloudView
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.adapters.RecyclerCloudAdapter
import kz.sgq.colorassistant.ui.fragment.dialog.*
import java.io.Serializable
import kz.sgq.colorassistant.ui.activity.ComboActivity
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

        initRecyclerAdapter(view.context)
    }

    override fun initColorList(list: MutableList<Cloud>) {

        adapter.addList(list)
    }

    override fun deleteItem(index: Int) {

        adapter.deleteItem(index)
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

    fun addItem(cloud: Cloud) {

        adapter.addItem(cloud)
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
}