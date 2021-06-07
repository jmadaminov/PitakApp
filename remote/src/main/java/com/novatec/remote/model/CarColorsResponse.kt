package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class CarColorsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarColorModel>? = null
)

data class CarColorModel(val id: Long,
                         val hex: String? = null,
                         @SerializedName("name") val name: String? = null)
