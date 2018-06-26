package kz.sgq.colorassistant.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_colors.*
import kz.sgq.colorassistant.R
import android.support.v7.app.AppCompatActivity
import kz.sgq.colorassistant.ui.adapters.SectionsPageAdapter
import kz.sgq.colorassistant.ui.util.interfaces.OnSelectedButtonListener


class MainFragment : Fragment(), OnSelectedButtonListener {
    private val colorsFragment = ColorsFragment()
    private val likesFragment = LikesFragment()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_colors, container, false)

    override fun onLikeClickListener(id: Int) {
        colorsFragment.dislike(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingToolBar()
        setupViewPager()
    }

    private fun settingToolBar() {
        val actionBar = (activity as AppCompatActivity)
        toolBar.title = getString(R.string.colors)
        actionBar.setSupportActionBar(toolBar)
    }

    private fun setupViewPager(){
        tabLayout.setupWithViewPager(viewPager)
        val adapter = SectionsPageAdapter(childFragmentManager)

        adapter.addFragment(
                colorsFragment,
                getString(R.string.fragmentColorsName)
        )

        adapter.addFragment(
                likesFragment,
                getString(R.string.fragmentLikesName)
        )

        viewPager.adapter = adapter
    }

}