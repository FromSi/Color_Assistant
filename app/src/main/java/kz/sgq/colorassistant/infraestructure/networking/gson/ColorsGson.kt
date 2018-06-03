package kz.sgq.colorassistant.infraestructure.networking.gson

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ColorsGson(
        @SerializedName("col_5")
        @Expose
        var col5: String? = null,
        @SerializedName("col_4")
        @Expose
        var col4: String? = null,
        @SerializedName("col_3")
        @Expose
        var col3: String? = null,
        @SerializedName("col_2")
        @Expose
        var col2: String? = null,
        @SerializedName("col_1")
        @Expose
        var col1: String? = null,
        @SerializedName("id_col")
        @Expose
        var idCol: String? = null,
        @SerializedName("check")
        @Expose
        var check: String? = null
)