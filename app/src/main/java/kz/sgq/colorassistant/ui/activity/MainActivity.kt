package kz.sgq.colorassistant.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.fragment.CollectionFragment
import kz.sgq.colorassistant.ui.fragment.ConstructorFragment
import kz.sgq.colorassistant.ui.fragment.MainFragment
import kz.sgq.colorassistant.ui.fragment.SettingsFragment


class MainActivity : AppCompatActivity() {

    private val colorsFragment = MainFragment()
    private val constructorFragment = ConstructorFragment()
    private val collectionFragment = CollectionFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        initClickMenuNavigation()
    }

    private fun initFragments(){
        nav_view.menu.getItem(0).isChecked = true
        stepFragments(colorsFragment)
    }

    private fun stepFragments(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initClickMenuNavigation() {
        nav_view.setNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.colors -> if (!item.isChecked) {
                    nav_view.menu.getItem(0).isChecked = true
                    nav_view.menu.getItem(1).isChecked = false
                    nav_view.menu.getItem(2).isChecked = false
                    nav_view.menu.getItem(3).isChecked = false
                    stepFragments(colorsFragment)
                }
                R.id.constructor -> if (!item.isChecked) {
                    nav_view.menu.getItem(0).isChecked = false
                    nav_view.menu.getItem(1).isChecked = true
                    nav_view.menu.getItem(2).isChecked = false
                    nav_view.menu.getItem(3).isChecked = false
                    stepFragments(constructorFragment)
                }
                R.id.collection -> if (!item.isChecked) {
                    nav_view.menu.getItem(0).isChecked = false
                    nav_view.menu.getItem(1).isChecked = false
                    nav_view.menu.getItem(2).isChecked = true
                    nav_view.menu.getItem(3).isChecked = false
                    stepFragments(collectionFragment)
                }
                R.id.settings -> if (!item.isChecked) {
                    nav_view.menu.getItem(0).isChecked = false
                    nav_view.menu.getItem(1).isChecked = false
                    nav_view.menu.getItem(2).isChecked = false
                    nav_view.menu.getItem(3).isChecked = true
                    stepFragments(settingsFragment)
                }
            }
            drawerLayout.closeDrawers()
            false
        })
    }
}
