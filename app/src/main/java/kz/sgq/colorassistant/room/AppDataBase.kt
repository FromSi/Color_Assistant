package kz.sgq.colorassistant.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kz.sgq.colorassistant.room.interfaces.CheckingDao
import kz.sgq.colorassistant.room.interfaces.ColorsDao
import kz.sgq.colorassistant.room.table.Checking
import kz.sgq.colorassistant.room.table.Colors

@Database(entities = [(Colors::class), (Checking::class)], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun colorsDao(): ColorsDao

    abstract fun updateDao(): CheckingDao
}