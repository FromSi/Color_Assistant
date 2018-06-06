package kz.sgq.colorassistant.ui.util.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.lang.Compiler.disable
import android.view.MotionEvent
import java.lang.Compiler.disable








class ComboViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
private var disable = true

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onTouchEvent(event)
    }

    fun disableScroll(disable: Boolean) {
        this.disable = disable
    }
}