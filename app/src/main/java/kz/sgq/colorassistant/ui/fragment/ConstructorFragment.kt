package kz.sgq.colorassistant.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_constructor.*
import kz.sgq.colorassistant.R

class ConstructorFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_constructor, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingToolBar()

        color_picker.addLightnessView(lightness)
        color_picker.addSaturationView(saturation)
    }
    private fun settingToolBar() {
        val actionBar = (activity as AppCompatActivity)
        toolBar.title = getString(R.string.constructor)
        actionBar.setSupportActionBar(toolBar)
    }
}