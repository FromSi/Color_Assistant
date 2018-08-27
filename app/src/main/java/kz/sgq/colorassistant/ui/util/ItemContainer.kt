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

package kz.sgq.colorassistant.ui.util

data class ItemContainer(
        var itemList: MutableList<ItemColor> = mutableListOf(),
        var likeList: MutableList<Boolean> = mutableListOf(),
        var visiblyList: MutableList<Boolean> = mutableListOf()
) {

    fun addItem(item: ItemColor) {

        itemList.add(item)
        likeList.add(item.like)
        visiblyList.add(true)
    }

    fun addItemList(list: MutableList<ItemColor>) {

        this.itemList.addAll(list)

        for (i in 0 until list.size) {

            likeList.add(list[i].like)
            visiblyList.add(true)
        }
    }

    fun setList(list: MutableList<ItemColor>) {

        this.itemList = list

        likeList = mutableListOf()
        visiblyList = mutableListOf()

        for (i in 0 until list.size) {

            likeList.add(list[i].like)
            visiblyList.add(true)
        }
    }
}