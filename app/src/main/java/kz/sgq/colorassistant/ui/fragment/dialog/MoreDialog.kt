package kz.sgq.colorassistant.ui.fragment.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog_qr.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.ui.util.ColorConverter
import kz.sgq.colorassistant.ui.view.ItemColor

class MoreDialog : DialogFragment() {
    private var index: Int = 0
    private var text: Array<String> = arrayOf("Hex", "RGB", "HSV", "CMYK")

    private lateinit var cloud: Cloud

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = resources.getString(R.string.dialog_more_title)
        val positive = resources.getString(R.string.dialog_more_positive)
        val dialog = AlertDialog.Builder(activity!!)
        val customLayout = activity!!.layoutInflater.inflate(R.layout.dialog_qr, null)

        dialog.setTitle(title)
        dialog.setView(customLayout)
        initView(customLayout)
        textClick(customLayout)

        dialog.setPositiveButton(positive) { _, _ -> }

        return dialog.create()
    }

    fun parseColor(view: View, color: Int) {
        text[0] = "Hex\n${ColorConverter.getHex(color)}"
        text[1] = "RGB\n${ColorConverter.getRGB(color)}"
        text[2] = "HSV\n${ColorConverter.getHSV(color)}"
        text[3] = "CMYK\n${ColorConverter.getCMYK(color)}"
        view.info.text = this.text[index]
    }

    fun cloud(cloud: Cloud) {
        this.cloud = cloud
    }

    private fun initView(view: View) {

        view.item_list.addView(createItem(view, Color.parseColor(cloud.colOne)))
        (view.item_list.getChildAt(0) as ItemColor).setEnable(true)
        parseColor(view, Color.parseColor(cloud.colOne))
        view.item_list.addView(createItem(view, Color.parseColor(cloud.colTwo)))
        view.item_list.addView(createItem(view, Color.parseColor(cloud.colThree)))

        if (cloud.colFour != null)
            view.item_list.addView(createItem(view, Color.parseColor(cloud.colFour)))

        if (cloud.colFive != null)
            view.item_list.addView(createItem(view, Color.parseColor(cloud.colFive)))
    }

    private fun textClick(view: View) {
        view.info.text = this.text[index]

        view.info.setOnClickListener {
            index++

            if (index <= 3)
                view.info.text = this.text[index]
            else {
                index = 0
                view.info.text = this.text[index]
            }
        }
    }

    private fun enableItems(view: View, itemColor: ItemColor) {

        for (i in 0 until view.item_list.childCount)
            (view.item_list.getChildAt(i) as ItemColor).setEnable(false)

        itemColor.setEnable(true)
    }

    private fun createItem(view: View, color: Int): View {
        val itemColor = ItemColor(context)

        itemColor.setColor(color)
        itemColor.setMoveItem(false)
        itemColor.setOnClickItemColorListener(initClick(view, color, itemColor))

        itemColor.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

        return itemColor
    }

    private fun initClick(
            view: View,
            color: Int,
            itemColor: ItemColor
    ): ItemColor.OnClickListener = object : ItemColor.OnClickListener {
        override fun onClick() {

            enableItems(view, itemColor)
            parseColor(view, color)
        }
    }
}