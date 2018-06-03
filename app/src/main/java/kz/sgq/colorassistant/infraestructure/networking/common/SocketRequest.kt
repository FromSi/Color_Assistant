package kz.sgq.colorassistant.infraestructure.networking.common

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sgq.colorassistant.infraestructure.networking.interfaces.SocketApiService

class SocketRequest(val api: SocketApiService) {

    fun getAllColors() = api.getAllColors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateCheck() = api.updateCheck()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateColors(check: Int) = api.updateColors(check)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}