package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [CarInPostModel] fetched from the API
 */
data class CarInPostModel(@SerializedName("id") var id: Long? = null,
                          @SerializedName("modelId") var modelId: Long? = null,
                          @SerializedName("image") var image: ImageModel? = null,
                          @SerializedName("carModel") var carModel: CarModelModel? = null,
                          @SerializedName("fuelType") var fuelType: String? = null,
                          @SerializedName("colorId") var colorId: Long? = null,
                          @SerializedName("carNumber") var carNumber: String? = null,
                          @SerializedName("carYear") var carYear: Int? = null,
                          @SerializedName("airConditioner") var airConditioner: Boolean? = null)
