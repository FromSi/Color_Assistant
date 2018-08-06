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

package kz.sgq.colorassistant.application

import android.app.Application
import android.arch.persistence.room.Room
import kz.sgq.colorassistant.room.AppDataBase

class App : Application() {
    private lateinit var dataBase: AppDataBase

    companion object {
        private var instance: App? = null

        fun getInstance() = instance
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(this, AppDataBase::class.java, "local")
                .build()
    }

    fun getDataBase() = dataBase
}