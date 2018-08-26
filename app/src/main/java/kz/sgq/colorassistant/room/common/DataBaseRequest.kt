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

package kz.sgq.colorassistant.room.common

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.sgq.colorassistant.application.App
import kz.sgq.colorassistant.mvp.model.ConstructorModelImpl
import kz.sgq.colorassistant.mvp.model.fragment.CloudModelImpl
import kz.sgq.colorassistant.room.table.Checking
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.room.table.Colors

object DataBaseRequest {
    private val dataBase = App.getInstance()?.getDataBase()

    interface OnEventListener {

        fun onSuccess()

        fun onError()
    }

    fun insertColors(colors: MutableList<Colors>): Completable =
            Completable.fromAction { dataBase?.colorsDao()?.insert(colors) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    fun insertUpdate(checking: Checking) {

        Completable.fromAction { dataBase?.updateDao()?.insert(checking) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun insertCloud(cloud: Cloud, eventListener: OnEventListener) {

        Completable.fromAction { dataBase?.cloudDao()?.insert(cloud) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {

                    override fun onComplete() {

                        eventListener.onSuccess()
                    }

                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(e: Throwable) {

                        eventListener.onError()
                    }
                })
    }

    fun deleteCloud(cloud: Cloud) {

        Completable.fromAction { dataBase?.cloudDao()?.delete(cloud) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun updateColors(idCol: Int, like: Boolean) {

        Completable.fromAction { dataBase?.colorsDao()?.update(idCol, like) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateUpdate(first: Int, last: Int) {

        Completable.fromAction { dataBase?.updateDao()?.update(first, last) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getColors(colorIds: IntArray) = dataBase?.colorsDao()
            ?.getColors(colorIds)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

    fun getColors() = dataBase?.colorsDao()
            ?.getColors()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())


    fun getColors(like: Boolean) = dataBase?.colorsDao()
            ?.getColors(like)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

    fun getColor() = dataBase?.cloudDao()
            ?.getColor()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())


    fun getUpdate() = dataBase?.updateDao()
            ?.getCheck()
            ?.subscribeOn(Schedulers.io())

    fun getCloud() = dataBase?.cloudDao()
            ?.getColors()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
}