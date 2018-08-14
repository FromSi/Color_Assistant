package kz.sgq.colorassistant.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import kz.sgq.colorassistant.R
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val helper = SwipeBackActivityHelper(this)

        helper.onActivityCreate()
        setContentView(R.layout.activity_settings)
        helper.swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        initActionBar()
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

    private fun initActionBar() {
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
