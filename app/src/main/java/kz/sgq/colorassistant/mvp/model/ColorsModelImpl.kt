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

import kz.sgq.colorassistant.infraestructure.networking.gson.ColorsGson
import kz.sgq.colorassistant.mvp.model.interfaces.ColorsModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.RandomItemsImpl
import kz.sgq.colorassistant.ui.util.interfaces.RandomItems
import java.util.ArrayList

class ColorsModelImpl : ColorsModel {

    private var update = 1
    private var loading = true
    private var idList: MutableList<Int> = arrayListOf()
    private var likeList: MutableList<Boolean> = arrayListOf()
    private lateinit var randomItems: RandomItems

    init {
        DataBaseRequest.getUpdate()
                ?.subscribe {update = it.check}
    }

    override fun initRandom(size: Int) {
        randomItems = RandomItemsImpl(size)
    }

    override fun getIdList(): MutableList<Int> = idList

    override fun getLikeList(): MutableList<Boolean> = likeList

    override fun getNumbers(): IntArray = randomItems.getNumbers()

    override fun getCheck(): Int = update

    override fun clear() {
        idList.clear()
        likeList.clear()
    }

    override fun getItemColor(colors: MutableList<Colors>): MutableList<ItemColor> {
        val list = ArrayList<ItemColor>()

        for (i in colors.indices) {
            val stringList = ArrayList<String>()
            stringList.add(colors[i].colOne)
            stringList.add(colors[i].colTwo)
            stringList.add(colors[i].colThree)

            if (colors[i].colFour != null)
                stringList.add(colors[i].colFour!!)

            if (colors[i].colFive != null)
                stringList.add(colors[i].colFive!!)

            list.add(ItemColor(colors[i].idCol,
                    stringList, colors[i].like!!))

            idList.add(colors[i].idCol)
            likeList.add(colors[i].like!!)
        }
        return list
    }

    override fun setLike(index: Int, like: Boolean) {
        likeList[index] = like
    }

    override fun isLoading(): Boolean = loading

    override fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun setRandomSize(size: Int) {
        randomItems.resize(size)
    }

    override fun getVisibleThreshold(): Int = 1

    override fun converterToItemColor(list: MutableList<ColorsGson>): MutableList<ItemColor> {
        val itemColorList = ArrayList<ItemColor>()
        for (i in list.indices) {
            val stringList = ArrayList<String>()
            stringList.add(list[i].col1!!)
            stringList.add(list[i].col2!!)
            stringList.add(list[i].col3!!)

            if (list[i].col4 != null)
                stringList.add(list[i].col4!!)

            if (list[i].col5 != null)
                stringList.add(list[i].col5!!)

            val itemColor = ItemColor(Integer
                    .parseInt(list[i].idCol),
                    stringList, false)
            itemColorList.add(itemColor)

            idList.add(Integer
                    .parseInt(list[i].idCol))
            likeList.add(false)
        }
        return itemColorList
    }

}