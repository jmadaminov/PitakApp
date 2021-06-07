package com.novatec.remote.model

import com.google.gson.annotations.SerializedName
import com.novatec.core.EPostStatus
import com.novatec.core.EPostType

/**
 * Representation for a [DriverPostModel] fetched from the API
 */
data class DriverPostModel(@SerializedName("id") val id: Long,
                           @SerializedName("from") val from: PlaceModel,
                           @SerializedName("to") val to: PlaceModel,
                           @SerializedName("price") val price: Int,
                           @SerializedName("departureDate") val departureDate: String,
                           @SerializedName("finishedDate") val finishedDate: String? = null,
                           @SerializedName("timeFirstPart") val timeFirstPart: Boolean,
                           @SerializedName("timeSecondPart") val timeSecondPart: Boolean,
                           @SerializedName("timeThirdPart") val timeThirdPart: Boolean,
                           @SerializedName("timeFourthPart") val timeFourthPart: Boolean,
                           @SerializedName("carId") val carId: Long? = null,
                           @SerializedName("car") val car: CarInPostModel? = null,
                           @SerializedName("remark") val remark: String? = null,
                           @SerializedName("seat") val seat: Int,
                           @SerializedName("offerCount") val offerCount: Int? = null,
                           @SerializedName("passengerCount") val passengerCount: Int? = null,
                           @SerializedName("availableSeats") val availableSeats: Int? = null,
                           @SerializedName("postStatus") val postStatus: EPostStatus? = null,
                           @SerializedName("pkg") val pkg: Boolean? = null,
                           @SerializedName("parcelCount") val parcelCount: Int? = null,
                           @SerializedName("passengerList") val passengerList: List<PassengerModel>? = null,
                           @SerializedName("parcelList") val parcelList: List<ParcelModel>? = null,
                           @SerializedName("postType") val postType: EPostType) {
}