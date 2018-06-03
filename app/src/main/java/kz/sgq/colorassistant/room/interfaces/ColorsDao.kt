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