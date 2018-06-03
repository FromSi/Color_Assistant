package kz.sgq.colorassistant.infraestructure.networking.common

import kz.sgq.colorassistant.infraestructure.networking.interfaces.SocketApiService

object ControllerApi {
    fun provider() = SocketRequest(SocketApiService.create())
}