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

package kz.sgq.colorassistant.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kz.sgq.colorassistant.room.interfaces.CheckingDao
import kz.sgq.colorassistant.room.interfaces.CloudDao
import kz.sgq.colorassistant.room.interfaces.ColorsDao
import kz.sgq.colorassistant.room.table.Checking
import kz.sgq.colorassistant.room.table.Cloud
import kz.sgq.colorassistant.room.table.Colors

@Database(entities = [(Colors::class), (Checking::class), (Cloud::class)], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun colorsDao(): ColorsDao

    abstract fun updateDao(): CheckingDao

    abstract fun cloudDao(): CloudDao
}