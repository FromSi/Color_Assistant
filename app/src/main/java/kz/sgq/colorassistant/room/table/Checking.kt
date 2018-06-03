package kz.sgq.colorassistant.room.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Checking(@PrimaryKey var check: Int = 0)