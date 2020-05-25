package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class CarModelsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarModelModel>? = null
)

data class CarModelModel(val id: Int,
                         val name: String)
