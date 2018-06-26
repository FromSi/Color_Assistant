package kz.sgq.colorassistant.ui.activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.adapters.MenuPageAdapter
import kz.sgq.colorassistant.ui.fragment.ConstructorFragment
import kz.sgq.colorassistant.ui.fragment.MainFragment
import kz.sgq.colorassistant.ui.fragment.SettingsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        setupTab()
    }

    private fun setupViewPager() {
        val menu = MenuPageAdapter(supportFragmentManager)
        menu.addFragment(MainFragment())
        menu.addFragment(ConstructorFragment())
        menu.addFragment(SettingsFragment())
        viewPager.adapter = menu
    }

    private fun setupTab() {
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.icon = ContextCompat.getDrawable(this, R.drawable.colors)
        tabLayout.getTabAt(1)?.icon = ContextCompat.getDrawable(this, R.drawable.constructor)
        tabLayout.getTabAt(2)?.icon = ContextCompat.getDrawable(this, R.drawable.settings)

        tabLayout.getTabAt(0)?.icon?.setColorFilter(
                ContextCompat.getColor(this, R.color.icons),
                PorterDuff.Mode.SRC_IN
        )

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val colorDef = ContextCompat.getColor(applicationContext, R.color.icon_def)
                tab?.icon?.setColorFilter(colorDef, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val color = ContextCompat.getColor(applicationContext, R.color.icons)
                tab?.icon?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        })
    }
}
