package kz.sgq.colorassistant.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private var fragmentList: MutableList<Fragment> = arrayListOf()
    private var titleList: MutableList<String> = arrayListOf()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size
}