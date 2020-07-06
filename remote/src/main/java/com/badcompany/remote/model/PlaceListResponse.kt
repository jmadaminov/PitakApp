package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class PlaceListResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<PlaceModel>? = null)

data class PlaceModel(@SerializedName("districtId") val districtId: Int?=null,
                      @SerializedName("regionId") val regionId: Int?=null,
                      @SerializedName("lat") val lat: Double? = null,
                      @SerializedName("lon") val lon: Double? = null,
                      @SerializedName("regionName") val regionName: String? = null,
                      @SerializedName("districtName") val districtName: String? = null
)
