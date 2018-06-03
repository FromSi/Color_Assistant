package kz.sgq.colorassistant.infraestructure.networking.interfaces

import io.reactivex.Observable
import kz.sgq.colorassistant.infraestructure.networking.gson.ColorsGson
import kz.sgq.colorassistant.infraestructure.networking.gson.UpdateGson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SocketApiService {
    @GET("colors")
    fun getAllColors(): Observable<MutableList<ColorsGson>>

    @GET("update")
    fun updateCheck(): Observable<UpdateGson>

    @GET("check")
    fun updateColors(@Query("update") check: Int): Observable<MutableList<ColorsGson>>

    companion object {
        fun create(): SocketApiService = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://colorsapp-sgq.herokuapp.com/")
                .build()
                .create(SocketApiService::class.java)
    }
}