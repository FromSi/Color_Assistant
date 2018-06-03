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