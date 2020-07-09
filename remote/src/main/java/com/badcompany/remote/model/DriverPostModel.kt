package com.badcompany.remote.model

import com.badcompany.core.Constants
import com.google.gson.annotations.SerializedName

/**
 * Representation for a [DriverPostModel] fetched from the API
 */
data class DriverPostModel(@SerializedName("id") val id: Long?=null,
                           @SerializedName("from") val from: PlaceModel,
                           @SerializedName("to") val to: PlaceModel,
                           @SerializedName("price") val price: Int,
                           @SerializedName("departureDate") val departureDate: String,
                           @SerializedName("finishedDate") val finishedDate: String?=null,
                           @SerializedName("timeFirstPart") val timeFirstPart: Boolean,
                           @SerializedName("timeSecondPart") val timeSecondPart: Boolean,
                           @SerializedName("timeThirdPart") val timeThirdPart: Boolean,
                           @SerializedName("timeFourthPart") val timeFourthPart: Boolean,
                           @SerializedName("carId") val carId: Long?=null,
                           @SerializedName("car") val car: CarInPostModel?=null,
                           @SerializedName("remark") val remark: String,
                           @SerializedName("seat") val seat: Int,
                           @SerializedName("postType") val postType: String = Constants.DRIVER_POST_SIMPLE)