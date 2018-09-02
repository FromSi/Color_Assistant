package kz.sgq.colorassistant.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.f_settings.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.SettingsPresenter
import kz.sgq.colorassistant.mvp.view.SettingsView
import kz.sgq.colorassistant.ui.util.java.PreferencesUtil
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class SettingsActivity : MvpAppCompatActivity(), SettingsView {
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(PreferencesUtil.getThemeId(this))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val helper = SwipeBackActivityHelper(this)

        helper.onActivityCreate()
        setContentView(R.layout.activity_settings)
        helper.swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        initActionBar()
        initItems()
        initClickMenu()
        helper.onPostCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {

            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initItems() {

        for (i in 0 until menu.childCount) {
            menu.getChildAt(i).title.text = resources.getStringArray(R.array.settings_title)[i]

            menu.getChildAt(i).icon.setImageDrawable(
                    resources
                            .obtainTypedArray(R.array.settings_icon)
                            .getDrawable(i)
            )
        }
    }

    private fun initClickMenu() {

        for (i in 0 until menu.childCount)
            menu.getChildAt(i).setOnClickListener {

                startActivity(
                        Intent(
                                this,
                                when (i) {
                                    0 -> SettingsThemeActivity::class.java
                                    else -> SettingsThemeActivity::class.java
                                }
                        )
                )
            }
    }

    private fun initActionBar() {
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
