package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class CarColorsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarColorModel>? = null
)

data class CarColorModel(val id: Long,
                         val hex: String,
                         @SerializedName("nameEn") val nameEn: String?=null,
                         @SerializedName("nameUz") val nameUz: String?=null,
                         @SerializedName("nameRu") val nameRu: String?=null)
