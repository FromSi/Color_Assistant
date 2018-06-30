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
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import kz.sgq.colorassistant.room.table.Checking

@Dao
interface CheckingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(checking: Checking)

    @Query("SELECT * FROM `checking`")
    fun getCheck(): Flowable<Checking>

    @Query("UPDATE `checking` SET `check`=:last WHERE `check`=:first")
    fun update(first: Int, last: Int)
}