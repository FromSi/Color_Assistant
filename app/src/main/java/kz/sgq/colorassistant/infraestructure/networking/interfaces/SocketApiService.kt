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

package kz.sgq.colorassistant.infraestructure.networking.interfaces

import io.reactivex.Observable
import kz.sgq.colorassistant.infraestructure.networking.gson.ColorsGson
import kz.sgq.colorassistant.infraestructure.networking.gson.UpdateGson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SocketApiService {

    @GET("colors")
    fun getAllColors(): Observable<MutableList<ColorsGson>>

    @GET("update")
    fun updateCheck(): Observable<UpdateGson>

    @GET("check")
    fun updateColors(@Query("update") check: Int): Observable<MutableList<ColorsGson>>

    companion object {

        fun create(): SocketApiService = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://color-assistant.herokuapp.com/")
                .build()
                .create(SocketApiService::class.java)
    }
}