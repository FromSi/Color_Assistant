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

package kz.sgq.colorassistant.infraestructure.networking.common

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sgq.colorassistant.infraestructure.networking.interfaces.SocketApiService

class SocketRequest(private val api: SocketApiService) {

    fun getAllColors() = api.getAllColors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun updateCheck() = api.updateCheck()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun updateColors(check: Int) = api.updateColors(check)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!
}