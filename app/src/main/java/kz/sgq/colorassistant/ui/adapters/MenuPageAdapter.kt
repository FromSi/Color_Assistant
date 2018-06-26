package kz.sgq.colorassistant.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MenuPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val list: MutableList<Fragment> = arrayListOf()

    fun addFragment(fragment: Fragment){
        list.add(fragment)
    }

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size
}