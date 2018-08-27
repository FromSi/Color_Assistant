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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_color_list.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.fragment.GlobalPresenter
import kz.sgq.colorassistant.mvp.view.fragment.GlobalView
import kz.sgq.colorassistant.ui.activity.ComboActivity
import kz.sgq.colorassistant.ui.adapters.RecyclerColorsAdapter
import kz.sgq.colorassistant.ui.util.ItemColor
import java.io.Serializable

class GlobalFragment : MvpAppCompatFragment(), GlobalView {
    private var adapter = RecyclerColorsAdapter()

    @InjectPresenter
    lateinit var presenter: GlobalPresenter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_color_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view.context)
        setUpLoadMoreListener()
        presenter.initPresenter()
    }

    override fun addList(list: MutableList<ItemColor>) {

        adapter.addItemList(list)
    }

    override fun showLoadDB() {
        download.visibility = View.VISIBLE
        rv_colors.visibility = View.GONE
    }

    override fun showColorList() {
        download.visibility = View.GONE
        rv_colors.visibility = View.VISIBLE
    }

    private fun setUpLoadMoreListener() {

        rv_colors.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                presenter.handlerColorListener(
                        layoutManager.itemCount,
                        layoutManager.findLastVisibleItemPosition()
                )
            }
        })
    }

    private fun initRecyclerView(context: Context) {
        layoutManager = LinearLayoutManager(context)
                .apply { orientation = LinearLayoutManager.VERTICAL }
        rv_colors.layoutManager = layoutManager
        rv_colors.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerColorsAdapter.OnClickListener {
            override fun onLike(id: Int, like: Boolean) {

                presenter.setLike(id, like)
            }

            override fun onView(itemColor: ItemColor) {

                startActivity(Intent(context, ComboActivity::class.java)
                        .apply { putExtra("map", itemColor.colors as Serializable) })
            }
        })
    }
}