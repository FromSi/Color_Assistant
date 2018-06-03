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