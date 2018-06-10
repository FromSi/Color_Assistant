package kz.sgq.colorassistant.mvp.model

import android.util.Log
import kz.sgq.colorassistant.mvp.model.interfaces.LikesModel
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.RandomItemsImpl
import kz.sgq.colorassistant.ui.util.interfaces.RandomItems

class LikesModelImpl : LikesModel {
    private var idList: MutableList<ItemColor> = arrayListOf()
    private var loading = true
    private var randomItems: RandomItems = RandomItemsImpl(0)

    override fun addIdList(item: Colors) {
        val stringList = ArrayList<String>()
        stringList.add(item.colOne)
        stringList.add(item.colTwo)
        stringList.add(item.colThree)

        if (item.colFour != null)
            stringList.add(item.colFour!!)

        if (item.colFive != null)
            stringList.add(item.colFive!!)

        idList.add(ItemColor(item.idCol,
                stringList, item.like!!))
        randomItems.resize(1)
        Log.d("TAG_LIKE", "addIdList ${idList.size}")
    }

    override fun getColorList(): MutableList<ItemColor> = idList

    override fun deleteIdList(index: Int) {
        randomItems.delete(index)
        idList.removeAt(index)
        Log.d("TAG_LIKE", "deleteIdList ${idList.size}")
    }

    override fun clearIdList() {
        idList.clear()
        randomItems.clear()
    }

    override fun getNumbers(): IntArray = randomItems.getNumbers()

    override fun isLoading(): Boolean = loading

    override fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun setRandomSize(size: Int) {
        randomItems.resize(size)
    }

    override fun getVisibleThreshold(): Int = 1
}