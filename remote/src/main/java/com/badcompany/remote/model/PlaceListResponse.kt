package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class PlaceListResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<PlaceModel>? = null)

data class PlaceModel(@SerializedName("districtId") val districtId: Int,
                      @SerializedName("regionId") val regionId: Int,
                      @SerializedName("nameRu") val nameRu: String? = null,
                      @SerializedName("nameUz") val nameUz: String? = null,
                      @SerializedName("nameEn") val nameEn: String? = null)
