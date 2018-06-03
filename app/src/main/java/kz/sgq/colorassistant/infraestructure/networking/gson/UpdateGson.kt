package kz.sgq.colorassistant.infraestructure.networking.gson

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateGson(
        @SerializedName("check")
                 @Expose
                 var check: String? = null
)