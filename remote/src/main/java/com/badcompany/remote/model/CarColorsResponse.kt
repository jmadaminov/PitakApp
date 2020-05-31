package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class CarColorsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarColorModel>? = null
)

data class CarColorModel(val id: Int,
                         val hex: String,
                         @SerializedName("nameEn") val nameEn: String,
                         @SerializedName("nameUz") val nameUz: String,
                         @SerializedName("nameRu") val nameRu: String)
