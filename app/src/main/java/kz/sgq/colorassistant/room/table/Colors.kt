package kz.sgq.colorassistant.room.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Colors(
        @PrimaryKey var idCol: Int,
        var colOne: String,
        var colTwo: String,
        var colThree: String,
        var colFour: String? = null,
        var colFive: String? = null,
        var like: Boolean? = false
)