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
        var itemList: MutableList<ItemColor> = arrayListOf(),
        var likeList: MutableList<Boolean> = arrayListOf(),
        var visiblyList: MutableList<Boolean> = arrayListOf()
) {
    fun addItems(itemList: MutableList<ItemColor>) {
        this.itemList.addAll(itemList)

        for (i in 0 until itemList.size) {
            likeList.add(itemList[i].like)
            visiblyList.add(true)
        }
    }

    fun addItem(item: ItemColor){
        itemList.add(item)
        likeList.add(item.like)
        visiblyList.add(true)
    }

    fun deleteItem(id: Int){
        for (i in 0 until itemList.size)
            if (itemList[i].id == id){
                itemList.removeAt(i)
                likeList.removeAt(i)
                visiblyList.removeAt(i)
                return
            }
    }

    fun clearItems() {
        itemList.clear()
        likeList.clear()
        visiblyList.clear()
    }

    fun dislike(id: Int) {
        for (i in 0 until itemList.size)
            if (itemList[i].id == id) {
                likeList[i] = false
                return
            }
    }

    fun updateItems(index: Int) {
        itemList[index].like = false
    }
}