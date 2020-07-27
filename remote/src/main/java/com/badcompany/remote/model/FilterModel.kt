package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [FilterModel] fetched from the API
 */
data class FilterModel(@SerializedName("airConditioner") var airConditioner: Boolean? = null,
                       @SerializedName("departureDate") var departureDate: String? = null,
                       @SerializedName("fromDistrictId") var fromDistrictId: Int? = null,
                       @SerializedName("fromRegionId") var fromRegionId: Int? = null,
                       @SerializedName("toDistrictId") var toDistrictId: Int? = null,
                       @SerializedName("toRegionId") var toRegionId: Int? = null,
                       @SerializedName("maxPrice") var maxPrice: Int? = null,
                       @SerializedName("minPrice") var minPrice: Int? = null,
                       @SerializedName("priceOrder") var priceOrder: String? = null,
                       @SerializedName("seat") var seat: Int? = null,
                       @SerializedName("timeFirstPart") var timeFirstPart: Boolean? = null,
                       @SerializedName("timeFourthPart") var timeFourthPart: Boolean? = null,
                       @SerializedName("timeSecondPart") var timeSecondPart: Boolean? = null,
                       @SerializedName("timeThirdPart") var timeThirdPart: Boolean? = null)