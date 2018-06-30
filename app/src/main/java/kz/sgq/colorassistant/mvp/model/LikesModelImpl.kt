/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.mvp.model

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
    }

    override fun getColorList(): MutableList<ItemColor> = idList

    override fun deleteIdList(id: Int) {
        randomItems.delete(id)
        idList.removeAt(id)
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