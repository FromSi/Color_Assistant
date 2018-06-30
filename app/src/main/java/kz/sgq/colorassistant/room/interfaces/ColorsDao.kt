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

package kz.sgq.colorassistant.room.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import kz.sgq.colorassistant.room.table.Colors

@Dao
interface ColorsDao {
    @Insert
    fun insert(colors: MutableList<Colors>)

    @Query("UPDATE `colors` SET `like`=:like WHERE `idCol`=:idCol")
    fun update(idCol: Int, like: Boolean)

    @Query("SELECT * FROM `colors` WHERE `idCol` IN (:colorIds)")
    fun getColors(colorIds: IntArray): Maybe<MutableList<Colors>>

    @Query("SELECT * FROM `colors`")
    fun getColors(): Maybe<MutableList<Colors>>

    @Query("SELECT * FROM `colors` WHERE `like`=:like")
    fun getColors(like: Boolean): Flowable<MutableList<Colors>>
}