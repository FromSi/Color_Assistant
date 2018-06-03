package kz.sgq.colorassistant.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_color_list.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.LikesPresenter
import kz.sgq.colorassistant.mvp.view.LikesView
import kz.sgq.colorassistant.ui.adapters.RecyclerColorsAdapter
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.interfaces.OnItemClickListener
import kz.sgq.colorassistant.ui.util.interfaces.OnSelectedButtonListener

class LikesFragment : MvpAppCompatFragment(), LikesView {
    @InjectPresenter
    lateinit var presenter: LikesPresenter

    private var adapter = RecyclerColorsAdapter()
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_color_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_colors.layoutManager = layoutManager
        rv_colors.adapter = adapter
        onClickListenerAdapter()
    }

    override fun clearItemsDB() {
        adapter.clearItems()
    }

    override fun showLoadDB() {
        textNoItem.visibility = View.VISIBLE
        rv_colors.visibility = View.GONE
    }

    override fun showColorList() {
        textNoItem.visibility = View.GONE
        rv_colors.visibility = View.VISIBLE
    }

    override fun addItemsDB(colorList: MutableList<ItemColor>) {
        adapter.addItems(colorList)
    }

    override fun showActivityInfo(list: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onClickListenerAdapter() {
        adapter.SetOnItemClickListener(object : OnItemClickListener {
            override fun onItemLikeClick(view: View, id: Int, like: Boolean) {
                val buttonListener = (activity as OnSelectedButtonListener)
                buttonListener.onLikeClickListener(id)
                adapter.setLike(id, true)
                presenter.onItemLikeClick(view, id, like)
            }

            override fun onItemViewClick(view: View, itemColor: ItemColor) {
                presenter.onItemViewClick(view, itemColor)
            }
        })
    }
}