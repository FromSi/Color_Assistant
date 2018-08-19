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

package kz.sgq.colorassistant.mvp.model.fragment

import kz.sgq.colorassistant.mvp.model.fragment.interfaces.BetaLikeModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor

class BetaLikeModelImpl : BetaLikeModel {

    interface OnAnswerListener {
        fun onAnswer(list: MutableList<ItemColor>)
    }

    override fun updateColor(id: Int, like: Boolean) {

        DataBaseRequest.updateColors(id, like)
    }

    override fun loadDB(answerListener: OnAnswerListener) {

        DataBaseRequest.getColors(true)
                ?.subscribe {

                    answerListener.onAnswer(handlerList(it))
                }
    }

    private fun handlerList(colors: MutableList<Colors>): MutableList<ItemColor> {
        val itemList: MutableList<ItemColor> = mutableListOf()

        for (i in 0 until colors.size)
            itemList.add(
                    ItemColor(
                            colors[i].idCol,
                            mutableListOf<String>().apply {
                                add(colors[i].colOne)
                                add(colors[i].colTwo)
                                add(colors[i].colThree)

                                if (colors[i].colFour != null)
                                    add(colors[i].colFour!!)

                                if (colors[i].colFive != null)
                                    add(colors[i].colFive!!)
                            },
                            colors[i].like!!
                    )
            )


        return itemList
    }
}