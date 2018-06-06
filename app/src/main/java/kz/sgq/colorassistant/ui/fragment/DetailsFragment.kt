package kz.sgq.colorassistant.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_details.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.RecyclerDetailsAdapter
import kz.sgq.colorassistant.ui.util.ItemDetails

class DetailsFragment : Fragment(){
    private var adapter = RecyclerDetailsAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(view.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        Log.d("TAG_VIEW", "DetailsFragment")
    }

    fun initAdapter(list: MutableList<ItemDetails>){
        adapter.addList(list)
    }
}