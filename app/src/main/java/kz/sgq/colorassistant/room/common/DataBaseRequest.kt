package kz.sgq.colorassistant.room.common

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sgq.colorassistant.application.App
import kz.sgq.colorassistant.room.table.Checking
import kz.sgq.colorassistant.room.table.Colors

object DataBaseRequest {
    private val dataBase = App.getInstance()?.getDataBase()

    fun insertColors(colors: MutableList<Colors>): Completable =
        Completable.fromAction({ dataBase?.colorsDao()?.insert(colors) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


    fun insertUpdate(checking: Checking) {
        Completable.fromAction({dataBase?.updateDao()?.insert(checking) })
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateColors(idCol: Int, like: Boolean) {
        Completable.fromAction({ dataBase?.colorsDao()?.update(idCol, like) })
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateUpdate(first: Int, last: Int) {
        Completable.fromAction({ dataBase?.updateDao()?.update(first, last) })
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getColors(colorIds: IntArray) = dataBase?.colorsDao()
            ?.getColors(colorIds)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

    fun getColors() = dataBase?.colorsDao()
            ?.getColors()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())


    fun getColors(like: Boolean) = dataBase?.colorsDao()
            ?.getColors(like)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())


    fun getUpdate() = dataBase?.updateDao()
            ?.getCheck()
            ?.subscribeOn(Schedulers.io())
}