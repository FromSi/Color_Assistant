/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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