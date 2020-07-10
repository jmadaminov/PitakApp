package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

data class PlaceModel(@SerializedName("districtId") val districtId: Int?=null,
                      @SerializedName("regionId") val regionId: Int?=null,
                      @SerializedName("lat") val lat: Double? = null,
                      @SerializedName("lon") val lon: Double? = null,
                      @SerializedName("regionName") val regionName: String? = null,
                      @SerializedName("districtName") val districtName: String? = null,
                      @SerializedName("name") val name: String? = null
)