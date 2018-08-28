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

import kz.sgq.colorassistant.infraestructure.networking.common.ControllerApi
import kz.sgq.colorassistant.infraestructure.networking.common.GsonConverter
import kz.sgq.colorassistant.mvp.model.fragment.interfaces.GlobalModel
import kz.sgq.colorassistant.room.common.DataBaseRequest
import kz.sgq.colorassistant.room.table.Checking
import kz.sgq.colorassistant.room.table.Colors
import kz.sgq.colorassistant.ui.util.ItemColor
import kz.sgq.colorassistant.ui.util.RandomItems

class GlobalModelImpl : GlobalModel {
    private var loading = true

    private lateinit var randomItems: RandomItems
    private lateinit var readListener: OnReadListener

    interface OnReadListener {

        fun onRead(list: MutableList<ItemColor>)
    }

    override fun setLike(id: Int, like: Boolean) {

        DataBaseRequest.updateLike(id, like)
    }

    override fun setReadListener(readListener: OnReadListener) {
        this.readListener = readListener
    }

    override fun handlerColorListener(itemCount: Int, lastVisibleItem: Int) {

        if (!loading &&
                (itemCount <= lastVisibleItem + 1) &&
                (lastVisibleItem != 0)) {
            loading = true

            getColorList()
        }
    }

    override fun initData() {

        DataBaseRequest.getColors()
                ?.subscribe {

                    if (it.size != 0) {
                        randomItems = RandomItems(it.size)

                        getColorList()
                    } else {

                        getAllColorsAPI()
                    }
                }
    }

    private fun getColorList() {

        DataBaseRequest.getColor(randomItems.onNumbers())
                ?.subscribe {

                    readListener.onRead(getItemColor(it))

                    loading = false
                }
    }

    private fun getItemColor(colors: MutableList<Colors>): MutableList<ItemColor> {
        val itemList = mutableListOf<ItemColor>()

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

    //Получаю все данные с сервера
    private fun getAllColorsAPI() {

        //Все цвета
        ControllerApi.provider()
                .getAllColors()
                .subscribe(
                        { it ->

                            //Записываю цвета в БД
                            DataBaseRequest
                                    .insertColors(GsonConverter.convertColorsList(it))
                                    .subscribe {

                                        //Запрашиваю последнее обновление (Номер)
                                        ControllerApi.provider().updateCheck().subscribe {

                                            //Записываю входящий номер последнего обновления
                                            DataBaseRequest.insertUpdate(Checking(it.check!!.toInt()))
                                        }
                                        //Возвращаюсь к запросу на получение локальной БД
                                        initData()
                                    }
                        },
                        //Если произошла ошибка на сервере или у пользователя не вкл интернет,
                        //то приложение будет обновлять запрос на ответ от сервера.
                        { getAllColorsAPI() })
    }
}