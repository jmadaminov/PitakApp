package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class CarColorsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarColorModel>? = null
)

data class CarColorModel(val id: Int,
                         val hex: Int,
                         val nameEn: String,
                         val nameUz: String,
                         val nameRu: String)
